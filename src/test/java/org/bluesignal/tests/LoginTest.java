package org.bluesignal.tests;

import org.bluesignal.core.BaseTest;
import org.bluesignal.pages.login.LoginPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest extends BaseTest {

    @Test
    public void shouldOpenLoginPage() {

        LoginPage loginPage = new LoginPage(driver).open();

        assertTrue(loginPage.isOpened());
        assertTrue(loginPage.form().isVisible());
    }

    @Test
    public void shouldDisplayLoginFormFields() {

        LoginPage loginPage = new LoginPage(driver).open();

        assertTrue(loginPage.form().isEmailInputVisible());
        assertTrue(loginPage.form().isPasswordInputVisible());
        assertTrue(loginPage.form().isSubmitButtonVisible());
        assertTrue(loginPage.form().isCreateProfileLinkVisible());
        assertTrue(loginPage.form().isForgotPasswordLinkVisible());
    }

    @Test
    public void shouldShowErrorMessageWithInvalidCredentials() {

        LoginPage loginPage = new LoginPage(driver).open();

        loginPage.form().login(
                "invalid_user_qa@example.com",
                "invalidPassword123"
        );

        assertTrue(loginPage.isOpened());
        assertTrue(loginPage.form().isErrorMessageVisible());
    }
}
