package org.bluesignal.core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class BaseTest {

    protected WebDriver driver;

    @BeforeEach
    public void setUp() {

        driver = DriverFactory.createDriver();

        driver.get("https://bluesignal.org");

        ((JavascriptExecutor) driver).executeScript(
                "localStorage.setItem('bluesignal_pwa_install_dismissed_v1', String(Date.now()));"
        );

        driver.navigate().refresh();
    }

    @AfterEach
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }
}
