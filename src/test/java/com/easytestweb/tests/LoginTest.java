package com.easytestweb.tests;

import com.easytestweb.pages.InventoryPage;
import com.easytestweb.pages.LoginPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("EasyTestWeb - Login Tests Suite")
public class LoginTest extends BaseTest {

    @Test
    @DisplayName("Should successfully log in with valid credentials")
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage(getPage()).open();

        InventoryPage inventoryPage = loginPage.loginAsValidUser("standard_user", "secret_sauce");

        assertThat(inventoryPage.isAt())
                .as("User should be redirected to the Products inventory page")
                .isTrue();

        assertThat(inventoryPage.getItemCount())
                .as("Products list should not be empty")
                .isGreaterThan(0);
    }

    @Test
    @DisplayName("Should show error message when login fails with invalid credentials")
    public void testInvalidPassword() {
        LoginPage loginPage = new LoginPage(getPage()).open();

        loginPage.loginExpectingFailure("standard_user", "invalid_password");

        assertThat(loginPage.isErrorDisplayed())
                .as("Error container should be visible")
                .isTrue();

        assertThat(loginPage.getErrorMessage())
                .as("Error text should inform about mismatched credentials")
                .contains("Username and password do not match");
    }

    @Test
    @DisplayName("Should show error message when attempting to log in as locked out user")
    public void testLockedOutUser() {
        LoginPage loginPage = new LoginPage(getPage()).open();

        loginPage.loginExpectingFailure("locked_out_user", "secret_sauce");

        assertThat(loginPage.isErrorDisplayed())
                .as("Error container should be visible")
                .isTrue();

        assertThat(loginPage.getErrorMessage())
                .as("Error text should state the user is locked out")
                .contains("Sorry, this user has been locked out");
    }

    @Test
    @DisplayName("Should show validation error when logging in with empty credentials")
    public void testEmptyCredentials() {
        LoginPage loginPage = new LoginPage(getPage()).open();

        loginPage.clickLogin();

        assertThat(loginPage.isErrorDisplayed())
                .as("Error container should be visible")
                .isTrue();

        assertThat(loginPage.getErrorMessage())
                .as("Error text should state username is required")
                .contains("Username is required");
    }
}
