package org.bluesignal.hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class Hooks {

    @Before
    public void beforeScenario(Scenario scenario) {
        System.out.println(
                "Iniciando escenario Cucumber: " + scenario.getName()
        );
    }

    @After
    public void afterScenario(Scenario scenario) {
        System.out.println(
                "Finalizando escenario Cucumber: "
                        + scenario.getName()
                        + " | Estado: "
                        + scenario.getStatus()
        );
    }
}