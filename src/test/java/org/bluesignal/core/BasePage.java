package org.bluesignal.core;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    protected void waitForVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForClickability(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void click(WebElement element) {
        waitForClickability(element);
        element.click();
    }

    protected String getText(WebElement element) {
        waitForVisibility(element);
        return element.getText();
    }

    protected boolean isDisplayed(WebElement element) {
        try {
            waitForVisibility(element);
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected void selectByVisibleText(WebElement selectElement, String visibleText) {
        waitForVisibility(selectElement);
        Select select = new Select(selectElement);
        select.selectByVisibleText(visibleText);
    }

    protected String getSelectedOptionText(WebElement selectElement) {
        waitForVisibility(selectElement);
        Select select = new Select(selectElement);
        return select.getFirstSelectedOption().getText();
    }

    protected void scrollTo(WebElement element) {
        waitForVisibility(element);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});",
                element
        );
    }

    protected void waitUntilUrlContains(String partialUrl) {
    wait.until(ExpectedConditions.urlContains(partialUrl));
    }

    protected boolean currentUrlContains(String partialUrl) {
    return getCurrentUrl().contains(partialUrl);
    }

    protected boolean isEnabled(WebElement element) {
    waitForVisibility(element);
    return element.isEnabled();
    }

    protected void type(WebElement element, String text) {
    waitForVisibility(element);
    element.clear();
    element.sendKeys(text);
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void navigateTo(String url) {
        driver.get(url);
    }
}
