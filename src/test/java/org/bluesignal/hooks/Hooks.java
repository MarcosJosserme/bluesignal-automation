package org.bluesignal.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.bluesignal.core.DriverContext;
import org.bluesignal.core.DriverFactory;
import org.openqa.selenium.WebDriver;

public class Hooks {

    @Before
    public void beforeScenario(Scenario scenario) {

        System.out.println(
                "Iniciando escenario Cucumber: " + scenario.getName()
        );

        WebDriver driver = DriverFactory.createDriver();

        DriverContext.setDriver(driver);
    }

    @After
    public void afterScenario(Scenario scenario) {

        try {
            System.out.println(
                    "Finalizando escenario Cucumber: "
                            + scenario.getName()
                            + " | Estado: "
                            + scenario.getStatus()
            );
        } finally {
            DriverContext.quitDriver();
        }
    }
}