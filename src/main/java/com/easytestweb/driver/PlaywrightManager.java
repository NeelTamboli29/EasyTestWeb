package com.easytestweb.driver;

import com.easytestweb.config.ConfigManager;
import com.microsoft.playwright.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;

/**
 * Thread-safe driver manager for Playwright instances, browsers, contexts, and pages.
 */
public final class PlaywrightManager {

    private static final Logger logger = LoggerFactory.getLogger(PlaywrightManager.class);

    private static final ThreadLocal<Playwright> playwrightThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<Browser> browserThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<BrowserContext> contextThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<Page> pageThreadLocal = new ThreadLocal<>();

    private PlaywrightManager() {
        // Utility class
    }

    public static Page initDriver() {
        String browserName = ConfigManager.getBrowser().toLowerCase();
        boolean headless = ConfigManager.isHeadless();
        double slowMo = ConfigManager.getSlowMo();

        logger.info("Initializing Playwright driver - Browser: {}, Headless: {}, SlowMo: {}ms",
                browserName, headless, slowMo);

        Playwright playwright = Playwright.create();
        playwrightThreadLocal.set(playwright);

        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions()
                .setHeadless(headless)
                .setSlowMo(slowMo);

        Browser browser;
        switch (browserName) {
            case "firefox":
                browser = playwright.firefox().launch(launchOptions);
                break;
            case "webkit":
            case "safari":
                browser = playwright.webkit().launch(launchOptions);
                break;
            case "chrome":
                launchOptions.setChannel("chrome");
                browser = playwright.chromium().launch(launchOptions);
                break;
            case "msedge":
            case "edge":
                launchOptions.setChannel("msedge");
                browser = playwright.chromium().launch(launchOptions);
                break;
            case "chromium":
            default:
                browser = playwright.chromium().launch(launchOptions);
                break;
        }
        browserThreadLocal.set(browser);

        Browser.NewContextOptions contextOptions = new Browser.NewContextOptions()
                .setViewportSize(ConfigManager.getViewportWidth(), ConfigManager.getViewportHeight());

        if (ConfigManager.isRecordVideo()) {
            contextOptions.setRecordVideoDir(Paths.get("target/videos"));
        }

        BrowserContext context = browser.newContext(contextOptions);
        context.setDefaultTimeout(ConfigManager.getTimeout());
        contextThreadLocal.set(context);

        if (ConfigManager.isTracing()) {
            context.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(true));
        }

        Page page = context.newPage();
        pageThreadLocal.set(page);

        logger.info("Driver initialization completed successfully.");
        return page;
    }

    public static Playwright getPlaywright() {
        return playwrightThreadLocal.get();
    }

    public static Browser getBrowser() {
        return browserThreadLocal.get();
    }

    public static BrowserContext getContext() {
        return contextThreadLocal.get();
    }

    public static Page getPage() {
        return pageThreadLocal.get();
    }

    public static void stopTracing(String traceFilePath) {
        BrowserContext context = getContext();
        if (context != null && ConfigManager.isTracing()) {
            try {
                context.tracing().stop(new Tracing.StopOptions()
                        .setPath(Paths.get(traceFilePath)));
                logger.info("Playwright trace captured at: {}", traceFilePath);
            } catch (Exception e) {
                logger.error("Failed to stop tracing: {}", e.getMessage());
            }
        }
    }

    public static void closeDriver() {
        logger.info("Closing Playwright driver instances...");

        Page page = pageThreadLocal.get();
        if (page != null) {
            try {
                page.close();
            } catch (Exception e) {
                logger.debug("Error closing page: {}", e.getMessage());
            }
            pageThreadLocal.remove();
        }

        BrowserContext context = contextThreadLocal.get();
        if (context != null) {
            try {
                context.close();
            } catch (Exception e) {
                logger.debug("Error closing context: {}", e.getMessage());
            }
            contextThreadLocal.remove();
        }

        Browser browser = browserThreadLocal.get();
        if (browser != null) {
            try {
                browser.close();
            } catch (Exception e) {
                logger.debug("Error closing browser: {}", e.getMessage());
            }
            browserThreadLocal.remove();
        }

        Playwright playwright = playwrightThreadLocal.get();
        if (playwright != null) {
            try {
                playwright.close();
            } catch (Exception e) {
                logger.debug("Error closing playwright: {}", e.getMessage());
            }
            playwrightThreadLocal.remove();
        }
    }
}
