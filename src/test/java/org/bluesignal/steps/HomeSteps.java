package org.bluesignal.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.bluesignal.core.DriverContext;
import org.bluesignal.pages.home.HomePage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomeSteps {

    private WebDriver driver;
    private HomePage homePage;

    @Given("el visitante no se encuentra autenticado")
    public void elVisitanteNoSeEncuentraAutenticado() {

        driver = DriverContext.getDriver();

        driver.manage().deleteAllCookies();

        assertTrue(
                driver.manage().getCookies().isEmpty(),
                "El navegador debería iniciar sin una sesión autenticada"
        );
    }

    @When("accede a la página principal de BlueSignal")
    public void accedeALaPaginaPrincipalDeBlueSignal() {

        driver.get("https://bluesignal.org");

        /*
         * Evita que el banner de instalación de la PWA
         * interfiera con las pruebas automatizadas.
         */
        ((JavascriptExecutor) driver).executeScript(
                "localStorage.setItem(" +
                "'bluesignal_pwa_install_dismissed_v1', " +
                "String(Date.now()));"
        );

        driver.navigate().refresh();

        homePage = new HomePage(driver);
    }

    @Then("la página principal se muestra correctamente")
    public void laPaginaPrincipalSeMuestraCorrectamente() {

        assertTrue(
                homePage.isLoaded(),
                "La página principal de BlueSignal no se cargó correctamente"
        );
    }
}