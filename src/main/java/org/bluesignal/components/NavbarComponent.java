package org.bluesignal.components;

import org.bluesignal.core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NavbarComponent extends BasePage {

    @FindBy(css = "img.logo-full[alt='BlueSignal']")
    private WebElement logo;

    @FindBy(css = "a.nav-link-home[href='/']")
    private WebElement homeLink;

    @FindBy(xpath = "//a[@data-report-link='1' and contains(@class,'button') and normalize-space()='Reportar']")
    private WebElement reportButton;

    @FindBy(css = "a.nav-profile-toggle[href='/login/']")
    private WebElement profileLoginButton;

    @FindBy(id = "nav-push-toggle")
    private WebElement pushNotificationButton;

    @FindBy(id = "theme-toggle")
    private WebElement themeToggleButton;

    @FindBy(id = "theme-icon")
    private WebElement themeIcon;

    public NavbarComponent(WebDriver driver) {
        super(driver);
    }

    public boolean isVisible() {
        return isDisplayed(logo)
                && isDisplayed(homeLink)
                && isDisplayed(reportButton)
                && isDisplayed(profileLoginButton);
    }

    public boolean isLogoVisible() {
        return isDisplayed(logo);
    }

    public boolean isHomeLinkVisible() {
        return isDisplayed(homeLink);
    }

    public boolean isReportButtonVisible() {
        return isDisplayed(reportButton);
    }

    public boolean isProfileLoginButtonVisible() {
        return isDisplayed(profileLoginButton);
    }

    public boolean isPushNotificationButtonVisible() {
        return isDisplayed(pushNotificationButton);
    }

    public boolean isPushNotificationButtonEnabled() {
        return isEnabled(pushNotificationButton);
    }

    public boolean isThemeToggleButtonVisible() {
        return isDisplayed(themeToggleButton);
    }

    public String getThemeIconText() {
        return getText(themeIcon);
    }

    public void goToHome() {
        click(homeLink);
        waitUntilUrlContains("bluesignal.org");
    }

    public void goToReport() {
        click(reportButton);
        waitUntilUrlContains("/reportar/");
    }

    public void goToLogin() {
        click(profileLoginButton);
        waitUntilUrlContains("/login/");
    }

    public void toggleTheme() {
        click(themeToggleButton);
    }
}
