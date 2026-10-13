package com.easytestweb.pages;

import com.easytestweb.utils.ScreenshotUtils;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base Page Object containing common interactions, fluent locators, and assertions.
 */
public abstract class BasePage {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected final Page page;

    public BasePage(Page page) {
        if (page == null) {
            throw new IllegalArgumentException("Page instance cannot be null when initializing Page Object");
        }
        this.page = page;
    }

    public Page getPage() {
        return page;
    }

    public void navigate(String url) {
        logger.info("Navigating to URL: {}", url);
        page.navigate(url);
    }

    public Locator locator(String selector) {
        return page.locator(selector);
    }

    public void click(String selector) {
        logger.debug("Clicking on element: {}", selector);
        page.locator(selector).click();
    }

    public void fill(String selector, String text) {
        logger.debug("Filling element '{}' with text: {}", selector, text);
        page.locator(selector).fill(text);
    }

    public void type(String selector, String text) {
        logger.debug("Typing into element '{}': {}", selector, text);
        page.locator(selector).pressSequentially(text);
    }

    public void press(String selector, String key) {
        logger.debug("Pressing key '{}' on element: {}", key, selector);
        page.locator(selector).press(key);
    }

    public void selectOption(String selector, String value) {
        logger.debug("Selecting option '{}' in dropdown: {}", value, selector);
        page.locator(selector).selectOption(value);
    }

    public void check(String selector) {
        logger.debug("Checking checkbox/radio: {}", selector);
        page.locator(selector).check();
    }

    public void uncheck(String selector) {
        logger.debug("Unchecking checkbox: {}", selector);
        page.locator(selector).uncheck();
    }

    public String getText(String selector) {
        return page.locator(selector).innerText().trim();
    }

    public String getInputValue(String selector) {
        return page.locator(selector).inputValue();
    }

    public String getAttribute(String selector, String attributeName) {
        return page.locator(selector).getAttribute(attributeName);
    }

    public boolean isVisible(String selector) {
        return page.locator(selector).isVisible();
    }

    public boolean isEnabled(String selector) {
        return page.locator(selector).isEnabled();
    }

    public void waitForSelector(String selector) {
        page.waitForSelector(selector, new Page.WaitForSelectorOptions().setState(WaitForSelectorState.VISIBLE));
    }

    public String getTitle() {
        return page.title();
    }

    public String getUrl() {
        return page.url();
    }

    public String takeScreenshot(String name) {
        return ScreenshotUtils.takeScreenshot(page, name);
    }
}
