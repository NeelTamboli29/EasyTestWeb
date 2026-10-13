package com.easytestweb.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Manages configuration properties for the EasyTestWeb framework.
 * System properties take precedence over properties defined in config.properties.
 */
public final class ConfigManager {

    private static final Logger logger = LoggerFactory.getLogger(ConfigManager.class);
    private static final Properties properties = new Properties();

    static {
        loadProperties();
    }

    private ConfigManager() {
        // Utility class
    }

    private static void loadProperties() {
        try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                logger.warn("config.properties not found in classpath. Relying on default values and system properties.");
            } else {
                properties.load(input);
                logger.info("Configuration properties successfully loaded.");
            }
        } catch (IOException ex) {
            logger.error("Failed to load config.properties", ex);
        }
    }

    public static String getProperty(String key, String defaultValue) {
        String systemVal = System.getProperty(key);
        if (systemVal != null && !systemVal.trim().isEmpty()) {
            return systemVal.trim();
        }
        return properties.getProperty(key, defaultValue);
    }

    public static String getBrowser() {
        return getProperty("browser", "chromium");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless", "true"));
    }

    public static String getBaseUrl() {
        return getProperty("baseUrl", "https://www.saucedemo.com");
    }

    public static double getTimeout() {
        return Double.parseDouble(getProperty("timeout", "30000"));
    }

    public static double getSlowMo() {
        return Double.parseDouble(getProperty("slowMo", "0"));
    }

    public static int getViewportWidth() {
        return Integer.parseInt(getProperty("viewportWidth", "1920"));
    }

    public static int getViewportHeight() {
        return Integer.parseInt(getProperty("viewportHeight", "1080"));
    }

    public static boolean isTracing() {
        return Boolean.parseBoolean(getProperty("tracing", "true"));
    }

    public static boolean isRecordVideo() {
        return Boolean.parseBoolean(getProperty("video", "false"));
    }

    public static boolean isScreenshotOnFailure() {
        return Boolean.parseBoolean(getProperty("screenshotOnFailure", "true"));
    }
}
