package org.bluesignal.tests;

import org.bluesignal.core.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocalityTest extends BaseTest {

    @Test
    public void shouldSelectLocality() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

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

        WebElement updatedSelect = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("locality-select")
                )
        );

        Select refreshedSelect = new Select(updatedSelect);

        String selectedOption =
                refreshedSelect.getFirstSelectedOption().getText();

        assertEquals("Mar del Plata", selectedOption);
    }
}