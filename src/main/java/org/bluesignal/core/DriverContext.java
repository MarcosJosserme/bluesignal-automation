package org.bluesignal.core;

import org.openqa.selenium.WebDriver;

public final class DriverContext {

    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    private DriverContext() {
        
    }

    public static void setDriver(WebDriver driver) {

        if (driver == null) {
            throw new IllegalArgumentException(
                    "El WebDriver no puede ser null"
            );
        }

        DRIVER.set(driver);
    }

    public static WebDriver getDriver() {

        WebDriver driver = DRIVER.get();

        if (driver == null) {
            throw new IllegalStateException(
                    "No existe un WebDriver inicializado para este escenario"
            );
        }

        return driver;
    }

    public static void quitDriver() {

        WebDriver driver = DRIVER.get();

        try {
            if (driver != null) {
                driver.quit();
            }
        } finally {
            DRIVER.remove();
        }
    }
}