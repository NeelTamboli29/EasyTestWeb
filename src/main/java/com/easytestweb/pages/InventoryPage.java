package com.easytestweb.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

import java.util.List;

/**
 * Page Object representing the Inventory / Products Page.
 */
public class InventoryPage extends BasePage {

    private static final String TITLE = ".title";
    private static final String INVENTORY_ITEM = ".inventory_item";
    private static final String INVENTORY_ITEM_NAME = ".inventory_item_name";
    private static final String SHOPPING_CART_BADGE = ".shopping_cart_badge";
    private static final String SORT_DROPDOWN = "[data-test='product-sort-container']";
    private static final String BURGER_MENU_BUTTON = "#react-burger-menu-btn";
    private static final String LOGOUT_SIDEBAR_LINK = "#logout_sidebar_link";

    public InventoryPage(Page page) {
        super(page);
    }

    public boolean isAt() {
        return isVisible(TITLE) && "Products".equalsIgnoreCase(getTitleText());
    }

    public String getTitleText() {
        return getText(TITLE);
    }

    public int getItemCount() {
        return locator(INVENTORY_ITEM).count();
    }

    public List<String> getAllItemNames() {
        return locator(INVENTORY_ITEM_NAME).allInnerTexts();
    }

    public void addItemToCart(String productName) {
        String buttonSelector = String.format("//div[text()='%s']/ancestor::div[@class='inventory_item']//button",
                productName);
        click(buttonSelector);
    }

    public String getCartItemCount() {
        if (isVisible(SHOPPING_CART_BADGE)) {
            return getText(SHOPPING_CART_BADGE);
        }
        return "0";
    }

    public void selectSortOption(String optionValue) {
        selectOption(SORT_DROPDOWN, optionValue);
    }

    public LoginPage logout() {
        click(BURGER_MENU_BUTTON);
        waitForSelector(LOGOUT_SIDEBAR_LINK);
        click(LOGOUT_SIDEBAR_LINK);
        return new LoginPage(page);
    }
}
