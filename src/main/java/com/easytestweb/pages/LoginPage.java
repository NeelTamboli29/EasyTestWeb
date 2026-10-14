package com.easytestweb.pages;

import com.microsoft.playwright.Page;

public class LoginPage {
    private final Page page;

    // Locators
    // Update these CSS selectors based on the actual DOM of easycater.co.in if
    // needed
    private final String loginMenuBtn = "text=Log In";
    private final String emailInput = "input[type='email']";
    private final String passwordInput = "input[type='password']";
    private final String submitButton = "button[type='submit']";

    // Constructor
    public LoginPage(Page page) {
        this.page = page;
    }

    // Actions
    public void navigateToHomePage() {
        page.navigate("https://test-www.easycater.co.in/");
    }

    public void clickLoginMenu() {
        // Clicks the initial login button/link on the homepage if there is one
        page.locator(loginMenuBtn).first().click();
    }

    public void enterEmail(String email) {
        page.locator(emailInput).fill(email);
    }

    public void enterPassword(String password) {
        page.locator(passwordInput).fill(password);
    }

    public void clickSubmit() {
        page.locator(submitButton).click();
    }

    // Combined Login Method
    public void loginToEasyCater(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSubmit();
    }
}
