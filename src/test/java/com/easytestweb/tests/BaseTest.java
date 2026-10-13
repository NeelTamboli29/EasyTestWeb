package com.easytestweb.tests;

import com.easytestweb.driver.PlaywrightManager;
import com.easytestweb.extensions.TestWatcherExtension;
import com.microsoft.playwright.Page;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base test class that handles browser and page setup/teardown for each test method.
 */
@ExtendWith(TestWatcherExtension.class)
public abstract class BaseTest {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected Page page;

    @BeforeEach
    public void setUp() {
        logger.info("Setting up browser test session...");
        page = PlaywrightManager.initDriver();
    }

    @AfterEach
    public void tearDown() {
        logger.info("Tearing down browser test session...");
        PlaywrightManager.closeDriver();
    }

    protected Page getPage() {
        return page;
    }
}
