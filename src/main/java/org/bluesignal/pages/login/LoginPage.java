package org.bluesignal.pages.login;

import org.bluesignal.core.BasePage;
import org.bluesignal.pages.login.components.LoginFormComponent;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private static final String LOGIN_URL = "https://bluesignal.org/login/";

    private final LoginFormComponent form;

    public LoginPage(WebDriver driver) {
        super(driver);
        this.form = new LoginFormComponent(driver);
    }

    public LoginPage open() {
        navigateTo(LOGIN_URL);
        return this;
    }

    public boolean isOpened() {
        return currentUrlContains("/login/");
    }

    public LoginFormComponent form() {
        return form;
    }
}
