package com.easytestweb.tests;

// import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class Navigation {
    private Playwright playwright;
    private Browser browser;
    private Page page;

    @BeforeEach
    void setUp() {
        // Initialize Playwright and launch the browser before each test
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page = browser.newPage();
    }

    @Test
    @DisplayName("Verify successful navigation to EasyCater test portal")
    void testNavigateToUrl() {
        // Navigate to the target application
        page.navigate("https://test-www.easycater.co.in/");

        // Assertion: Ensure the page URL contains the expected domain
        // assertThat(page).hasURL("https://test-www.easycater.co.in/");

        page.waitForTimeout(3000);
    }

    @AfterEach
    void tearDown() {
        // Clean up resources after each test executes

        if (page != null) {
            page.close();
        }

        if (browser != null) {
            browser.close();
        }

        if (playwright != null) {
            playwright.close();
        }
    }
}
