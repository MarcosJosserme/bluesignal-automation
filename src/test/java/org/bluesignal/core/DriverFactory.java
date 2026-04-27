package org.bluesignal.core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class DriverFactory {

    public static WebDriver createDriver() {

        String driverMode = System.getProperty("driver.mode", "auto");

        /*
         * Driver manual o automático
         */

        if (driverMode.equalsIgnoreCase("manual")) {

            String driverPath = Paths.get("drivers", "chromedriver.exe")
                    .toAbsolutePath()
                    .toString();

            System.setProperty("webdriver.chrome.driver", driverPath);

            System.out.println("Usando driver local: " + driverPath);

        } else {

            WebDriverManager.chromedriver().setup();

            System.out.println("Usando WebDriverManager");
        }

        /*
         * Configuración Chrome
         */

        ChromeOptions options = new ChromeOptions();

        options.addArguments("--start-maximized");

        /*
         * Evita ruido del navegador
         */

        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");

        /*
         * Preferencias navegador
         */

        Map<String, Object> prefs = new HashMap<>();

        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.default_content_setting_values.notifications", 2);

        options.setExperimentalOption("prefs", prefs);

        return new ChromeDriver(options);
    }
}