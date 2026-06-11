package org.bluesignal.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.bluesignal.core.DriverContext;
import org.bluesignal.pages.home.HomePage;
import org.bluesignal.pages.report.ReportPage;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReportSteps {

    private WebDriver driver;
    private HomePage homePage;
    private ReportPage reportPage;

    @When("selecciona Mar del Plata como localidad del reporte")
    public void seleccionaMarDelPlataComoLocalidadDelReporte() {

        driver = DriverContext.getDriver();
        homePage = new HomePage(driver);

        homePage.localitySelector().open();
        homePage.localitySelector().selectLocality("Mar del Plata");
        homePage.localitySelector()
                .waitUntilLocalityRouteIsLoaded("mar-del-plata");
    }

    @When("accede al formulario de reporte")
    public void accedeAlFormularioDeReporte() {

        homePage.navbar().goToReport();

        reportPage = new ReportPage(driver);
    }

    @Then("el formulario de reporte se muestra correctamente")
    public void elFormularioDeReporteSeMuestraCorrectamente() {

        assertTrue(
                reportPage.isOpened(),
                "La URL del formulario de reporte no se cargó correctamente"
        );

        assertTrue(
                reportPage.form().isVisible(),
                "El formulario de reporte no está visible"
        );
    }

    @Then("el botón de envío del reporte permanece deshabilitado")
    public void elBotonDeEnvioDelReportePermaneceDeshabilitado() {

        assertTrue(
                reportPage.isOpened(),
                "La página de reporte no se abrió correctamente"
        );

        assertFalse(
                reportPage.form().isSubmitButtonEnabled(),
                "El botón de envío no debería habilitarse con el formulario vacío"
        );
    }
}
