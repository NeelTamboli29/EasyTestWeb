package com.easytestweb.pages;

import com.microsoft.playwright.Page;

public class LoginPage {
    private final Page page;

    // Locators
    // Update these selector based on the exact DOM of the EasyCater login module

    private final String loginMenuBtn = "text=Log In"; // If there's an initial button to open the module

    // Step 1 Locators
    private final String mobileNumberInput = "await page.locator(\"//input[@placeholder='10-digit number']\")";
    private final String clickOnLoginBtn = "await page.locator('button:has-text(\"Log In\")')";

    // Step 2
    private final String otpInput = "input[placeholder*='OTP'], input[type='password']";
    private final String verifyOtpButton = "button:has-text('Verify'), button:has-text('Login')";

    // Constructor
    public LoginPage(Page page) {
        this.page = page;
    }

    // Actions
    public void navigateToHomePage() {
        page.navigate("https://test-www.easycater.co.in/");
    }

    public void clickLoginMenu() {
        page.locator(loginMenuBtn).first().click();
    }

    public void enterMobileNumber(String mobileNumber) {
        page.locator(mobileNumberInput).fill(mobileNumber);
    }

    public void clickOnLoginBtn() {
        page.locator(clickOnLoginBtn).click();
    }

    public void enterOtp(String otp) {
        // Crucial: Wait for the OTP field to become visible in the DOM before trying to
        // type
        page.locator(otpInput).waitFor();
        page.locator(otpInput).fill(otp);
    }

    public void clickVerifyOtp() {
        page.locator(verifyOtpButton).click();
    }

    // Combined Login Method
    public void loginWithMobileAndOtp(String mobileNumber, String otp) {
        enterMobileNumber(mobileNumber);
        clickOnLoginBtn();
        enterOtp(otp);
        clickVerifyOtp();
    }
}
