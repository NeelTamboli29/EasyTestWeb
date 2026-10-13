package com.easytestweb.utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility for capturing full-page and element-level screenshots.
 */
public final class ScreenshotUtils {

    private static final Logger logger = LoggerFactory.getLogger(ScreenshotUtils.class);
    private static final String SCREENSHOT_DIR = "target/screenshots";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");

    private ScreenshotUtils() {
        // Utility class
    }

    public static String takeScreenshot(Page page, String testName) {
        if (page == null) {
            logger.warn("Cannot capture screenshot: Page is null");
            return null;
        }

        try {
            File dir = new File(SCREENSHOT_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String sanitizedName = testName.replaceAll("[^a-zA-Z0-9.-]", "_");
            String timestamp = LocalDateTime.now().format(FORMATTER);
            String filePath = SCREENSHOT_DIR + "/" + sanitizedName + "_" + timestamp + ".png";

            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get(filePath))
                    .setFullPage(true));

            logger.info("Screenshot saved successfully at: {}", filePath);
            return filePath;
        } catch (Exception e) {
            logger.error("Failed to capture screenshot for '{}': {}", testName, e.getMessage());
            return null;
        }
    }

    public static String takeElementScreenshot(Locator locator, String elementName) {
        if (locator == null) {
            logger.warn("Cannot capture element screenshot: Locator is null");
            return null;
        }

        try {
            File dir = new File(SCREENSHOT_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            String sanitizedName = elementName.replaceAll("[^a-zA-Z0-9.-]", "_");
            String timestamp = LocalDateTime.now().format(FORMATTER);
            String filePath = SCREENSHOT_DIR + "/element_" + sanitizedName + "_" + timestamp + ".png";

            locator.screenshot(new Locator.ScreenshotOptions().setPath(Paths.get(filePath)));
            logger.info("Element screenshot saved at: {}", filePath);
            return filePath;
        } catch (Exception e) {
            logger.error("Failed to capture element screenshot for '{}': {}", elementName, e.getMessage());
            return null;
        }
    }
}
