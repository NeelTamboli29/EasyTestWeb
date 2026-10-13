package com.easytestweb.pages;

import com.easytestweb.config.ConfigManager;
import com.microsoft.playwright.Page;

/**
 * Page Object representing the Login Page (e.g., SauceDemo login).
 */
public class LoginPage extends BasePage {

    private static final String USERNAME_INPUT = "#user-name";
    private static final String PASSWORD_INPUT = "#password";
    private static final String LOGIN_BUTTON = "#login-button";
    private static final String ERROR_MESSAGE = "[data-test='error']";

    public LoginPage(Page page) {
        super(page);
    }

    public LoginPage open() {
        navigate(ConfigManager.getBaseUrl());
        return this;
    }

    public void enterUsername(String username) {
        fill(USERNAME_INPUT, username);
    }

    public void enterPassword(String password) {
        fill(PASSWORD_INPUT, password);
    }

    public void clickLogin() {
        click(LOGIN_BUTTON);
    }

    public InventoryPage loginAsValidUser(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        return new InventoryPage(page);
    }

    public LoginPage loginExpectingFailure(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        return this;
    }

    public boolean isErrorDisplayed() {
        return isVisible(ERROR_MESSAGE);
    }

    public String getErrorMessage() {
        return getText(ERROR_MESSAGE);
    }
}
