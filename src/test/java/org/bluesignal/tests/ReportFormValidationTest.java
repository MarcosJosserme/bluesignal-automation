package org.bluesignal.tests;

import org.bluesignal.core.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ReportFormValidationTest extends BaseTest {

    @Test
    public void shouldKeepSubmitButtonDisabledWhenFormIsEmpty() {

        WebDriverWait wait =
                new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement localityButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.id("locality-manual-btn")
                )
        );

        localityButton.click();

        WebElement localitySelect = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("locality-select-empty")
                )
        );

        Select select = new Select(localitySelect);
        select.selectByVisibleText("Mar del Plata");

        wait.until(
                ExpectedConditions.urlContains("/localidad/mar-del-plata/")
        );

        WebElement reportButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[@data-report-link='1' and normalize-space()='Reportar']")
                )
        );

        reportButton.click();

        wait.until(
                ExpectedConditions.urlContains("/reportar/")
        );

        WebElement submitButton = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.id("report-submit-btn")
                )
        );

        assertFalse(submitButton.isEnabled());
    }
}