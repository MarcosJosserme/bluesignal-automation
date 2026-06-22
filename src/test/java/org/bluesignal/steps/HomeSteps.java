package org.bluesignal.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import org.bluesignal.core.DriverContext;
import org.bluesignal.pages.home.HomePage;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomeSteps {

    private WebDriver driver;
    private HomePage homePage;

    @Given("el visitante no se encuentra autenticado")
    public void elVisitanteNoSeEncuentraAutenticado() {

        configurarMetadataHome();

        Allure.step("Obtener el driver activo", () -> {
            driver = DriverContext.getDriver();
        });

        Allure.step("Eliminar cookies del navegador para iniciar sin sesión autenticada", () -> {
            driver.manage().deleteAllCookies();
        });

        Allure.step("Validar que el navegador no conserve cookies de sesión", () -> {
            assertTrue(
                    driver.manage().getCookies().isEmpty(),
                    "El navegador debería iniciar sin una sesión autenticada"
            );
        });
    }

    @When("accede a la página principal de BlueSignal")
    public void accedeALaPaginaPrincipalDeBlueSignal() {

        configurarMetadataHome();

        Allure.step("Acceder a la página principal de BlueSignal", () -> {
            driver.get("https://bluesignal.org");
        });

        Allure.step("Marcar como descartado el banner de instalación PWA para evitar interferencias", () -> {
            /*
             * Evita que el banner de instalación de la PWA
             * interfiera con las pruebas automatizadas.
             */
            ((JavascriptExecutor) driver).executeScript(
                    "localStorage.setItem(" +
                            "'bluesignal_pwa_install_dismissed_v1', " +
                            "String(Date.now()));"
            );
        });

        Allure.step("Refrescar la página principal luego de configurar localStorage", () -> {
            driver.navigate().refresh();
        });

        Allure.step("Inicializar la Page Object de Home", () -> {
            homePage = new HomePage(driver);
        });

        adjuntarCapturaSiEsPosible();
    }

    @When("el visitante selecciona la localidad {string}")
    public void elVisitanteSeleccionaLaLocalidad(String localidad) {

        configurarMetadataHome();

        Allure.step("Abrir el selector de localidades", () -> {
            homePage.localitySelector().open();
        });

        Allure.step("Seleccionar la localidad: " + localidad, () -> {
            homePage.localitySelector().selectLocality(localidad);
        });

        adjuntarCapturaSiEsPosible();
    }

    @Then("la página principal se muestra correctamente")
    public void laPaginaPrincipalSeMuestraCorrectamente() {

        configurarMetadataHome();

        Allure.step("Validar que la página principal de BlueSignal se cargó correctamente", () -> {
            assertTrue(
                    homePage.isLoaded(),
                    "La página principal de BlueSignal no se cargó correctamente"
            );
        });

        adjuntarCapturaSiEsPosible();
    }

    @Then("el mapa principal de avistajes se muestra correctamente")
    public void elMapaPrincipalDeAvistajesSeMuestraCorrectamente() {

        configurarMetadataHome();

        Allure.step("Validar que la página principal terminó de cargar", () -> {
            assertTrue(
                    homePage.isLoaded(),
                    "La página principal no terminó de cargar"
            );
        });

        Allure.step("Desplazar la vista hasta el mapa principal", () -> {
            homePage.map().scrollIntoView();
        });

        Allure.step("Validar que el contenedor del mapa principal esté visible", () -> {
            assertTrue(
                    homePage.map().isVisible(),
                    "El contenedor del mapa principal no está visible"
            );
        });

        Allure.step("Validar que el canvas de MapLibre esté visible", () -> {
            assertTrue(
                    homePage.map().isCanvasVisible(),
                    "El canvas de MapLibre no está visible"
            );
        });

        adjuntarCapturaSiEsPosible();
    }

    @Then("la localidad {string} queda seleccionada")
    public void laLocalidadQuedaSeleccionada(String localidadEsperada) {

        configurarMetadataHome();

        Allure.step("Validar que la localidad seleccionada coincida con: " + localidadEsperada, () -> {
            assertEquals(
                    localidadEsperada,
                    homePage.localitySelector().getSelectedLocality(),
                    "La localidad seleccionada no coincide con la esperada"
            );
        });

        adjuntarCapturaSiEsPosible();
    }

    private void configurarMetadataHome() {
        Allure.label("epic", "BlueSignal");
        Allure.label("feature", "Página principal");
        Allure.label("story", "Visualización y navegación inicial de BlueSignal");
        Allure.label("severity", "critical");
        Allure.description(
                "Validar la carga inicial de BlueSignal, la visualización de la página principal, " +
                        "el mapa de avistajes y la selección de localidades."
        );
    }

    private void adjuntarCapturaSiEsPosible() {
        if (driver == null) {
            driver = DriverContext.getDriver();
        }

        byte[] screenshot = ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.BYTES);

        Allure.addAttachment(
                "Captura de pantalla",
                "image/png",
                new ByteArrayInputStream(screenshot),
                ".png"
        );
    }
}