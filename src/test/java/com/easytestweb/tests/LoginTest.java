package com.easytestweb.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.easytestweb.pages.LoginPage;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class LoginTest {
    private Playwright playwright;
    private Browser browser;
    private Page page;
    private LoginPage loginPage;

    @BeforeEach
    public void setUp() {
        // Initialize Playwright and lanch the browser
        playwright = Playwright.create();

        // SetHeadless(false) lets you watch the execution on the screen
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page = browser.newPage();

        // Initialize page object
        loginPage = new LoginPage(page);
    }

    @Test
    public void validTestLogin() {
        // 1. Navigate to URL
        loginPage.navigateToHomePage();

        // Optional: Click the initial login trigger if the form isn't natively on the
        // homepage
        // loginPage.clickLoginMenu();

        // 2. Execute the OTP login Flow
        loginPage.loginWithMobileAndOtp("9797979797", "111111");

        // 3. Assertion: Verify login was successfull
        // Example 1: Verify the URL changed to the dashboard
        // assertThat(page).hasURL(Pattern.compile(".*dashboard.*"));

        // Example 2: Verify a specific element on the post-login page is visible
        // assertThat(page.locator("text=Welcome, Neel")).isVisible();

        page.waitForTimeout(3000);

    }

    @AfterEach
    public void tearDown() {
        browser.close();
        playwright.close();
    }

}
