package org.bluesignal.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.bluesignal.core.DriverContext;
import org.bluesignal.pages.home.HomePage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @When("el visitante selecciona la localidad {string}")
    public void elVisitanteSeleccionaLaLocalidad(String localidad) {

        homePage.localitySelector().open();
        homePage.localitySelector().selectLocality(localidad);
    }

    @Then("la página principal se muestra correctamente")
    public void laPaginaPrincipalSeMuestraCorrectamente() {

        assertTrue(
                homePage.isLoaded(),
                "La página principal de BlueSignal no se cargó correctamente"
        );
    }

    @Then("el mapa principal de avistajes se muestra correctamente")
    public void elMapaPrincipalDeAvistajesSeMuestraCorrectamente() {

        assertTrue(
                homePage.isLoaded(),
                "La página principal no terminó de cargar"
        );

        homePage.map().scrollIntoView();

        assertTrue(
                homePage.map().isVisible(),
                "El contenedor del mapa principal no está visible"
        );

        assertTrue(
                homePage.map().isCanvasVisible(),
                "El canvas de MapLibre no está visible"
        );
    }

    @Then("la localidad {string} queda seleccionada")
    public void laLocalidadQuedaSeleccionada(String localidadEsperada) {

        assertEquals(
                localidadEsperada,
                homePage.localitySelector().getSelectedLocality(),
                "La localidad seleccionada no coincide con la esperada"
        );
    }
}