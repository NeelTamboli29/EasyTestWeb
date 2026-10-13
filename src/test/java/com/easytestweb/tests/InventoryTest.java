package com.easytestweb.tests;

import com.easytestweb.pages.InventoryPage;
import com.easytestweb.pages.LoginPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("EasyTestWeb - Inventory & Navigation Tests")
public class InventoryTest extends BaseTest {

    private InventoryPage inventoryPage;

    @BeforeEach
    public void loginBeforeTest() {
        LoginPage loginPage = new LoginPage(getPage()).open();
        inventoryPage = loginPage.loginAsValidUser("standard_user", "secret_sauce");
    }

    @Test
    @DisplayName("Should add an item to the shopping cart and update cart badge")
    public void testAddProductToCart() {
        assertThat(inventoryPage.getCartItemCount()).isEqualTo("0");

        inventoryPage.addItemToCart("Sauce Labs Backpack");

        assertThat(inventoryPage.getCartItemCount())
                .as("Cart badge should show 1 item")
                .isEqualTo("1");
    }

    @Test
    @DisplayName("Should display all products and allow sorting")
    public void testProductsListAndSorting() {
        List<String> items = inventoryPage.getAllItemNames();

        assertThat(items)
                .as("Product catalog should have multiple items")
                .hasSizeGreaterThanOrEqualTo(6);

        inventoryPage.selectSortOption("lohi");

        List<String> sortedItems = inventoryPage.getAllItemNames();
        assertThat(sortedItems).isNotEmpty();
    }

    @Test
    @DisplayName("Should allow user to log out and return to the login screen")
    public void testUserLogout() {
        LoginPage loginPage = inventoryPage.logout();

        assertThat(loginPage.getUrl())
                .as("After logout, URL should point to base login")
                .contains("saucedemo.com");

        assertThat(loginPage.isVisible("#login-button"))
                .as("Login button should be visible again")
                .isTrue();
    }
}
