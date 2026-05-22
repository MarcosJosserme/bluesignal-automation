package org.bluesignal.pages.login.components;

import org.bluesignal.core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginFormComponent extends BasePage {

    @FindBy(tagName = "form")
    private WebElement form;

    @FindBy(id = "id_email")
    private WebElement emailInput;

    @FindBy(id = "id_password")
    private WebElement passwordInput;

    @FindBy(css = "button[type='submit']")
    private WebElement submitButton;

    @FindBy(css = "a[href='/perfil/crear/']")
    private WebElement createProfileLink;

    @FindBy(css = "a[href='/password-reset/']")
    private WebElement forgotPasswordLink;

    @FindBy(css = ".errorlist, .alert, [role='alert']")
    private WebElement errorMessage;

    public LoginFormComponent(WebDriver driver) {
        super(driver);
    }

    public boolean isVisible() {
        return isDisplayed(form);
    }

    public boolean isEmailInputVisible() {
        return isDisplayed(emailInput);
    }

    public boolean isPasswordInputVisible() {
        return isDisplayed(passwordInput);
    }

    public boolean isSubmitButtonVisible() {
        return isDisplayed(submitButton);
    }

    public boolean isCreateProfileLinkVisible() {
        return isDisplayed(createProfileLink);
    }

    public boolean isForgotPasswordLinkVisible() {
        return isDisplayed(forgotPasswordLink);
    }

    public void login(String email, String password) {
        type(emailInput, email);
        type(passwordInput, password);
        click(submitButton);
    }

    public boolean isErrorMessageVisible() {
        return isDisplayed(errorMessage);
    }
}