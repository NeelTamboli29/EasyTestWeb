package com.easytestweb.extensions;

import com.easytestweb.config.ConfigManager;
import com.easytestweb.driver.PlaywrightManager;
import com.easytestweb.utils.ScreenshotUtils;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Optional;

/**
 * JUnit 5 extension that monitors test executions and captures screenshots and traces on failure.
 */
public class TestWatcherExtension implements TestWatcher {

    private static final Logger logger = LoggerFactory.getLogger(TestWatcherExtension.class);

    @Override
    public void testSuccessful(ExtensionContext context) {
        logger.info("TEST PASSED: {}", context.getDisplayName());
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        String testName = context.getRequiredTestClass().getSimpleName() + "_" + context.getRequiredTestMethod().getName();
        logger.error("TEST FAILED: {} - Reason: {}", testName, cause.getMessage());

        if (ConfigManager.isScreenshotOnFailure() && PlaywrightManager.getPage() != null) {
            String screenshotPath = ScreenshotUtils.takeScreenshot(PlaywrightManager.getPage(), testName + "_FAILED");
            logger.info("Failure screenshot captured: {}", screenshotPath);
        }

        if (ConfigManager.isTracing()) {
            File traceDir = new File("target/traces");
            if (!traceDir.exists()) {
                traceDir.mkdirs();
            }
            String tracePath = "target/traces/" + testName + "_trace.zip";
            PlaywrightManager.stopTracing(tracePath);
        }
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        logger.warn("TEST ABORTED: {}", context.getDisplayName());
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        logger.warn("TEST DISABLED: {} - Reason: {}", context.getDisplayName(), reason.orElse("No reason provided"));
    }
}
