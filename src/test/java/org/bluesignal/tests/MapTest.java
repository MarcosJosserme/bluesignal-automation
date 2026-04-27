package org.bluesignal.tests;

import org.bluesignal.core.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MapTest extends BaseTest {

    @Test
    public void shouldDisplayMainMap() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement map = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("dashboard-map")
                )
        );

        assertTrue(map.isDisplayed());
    }
}
