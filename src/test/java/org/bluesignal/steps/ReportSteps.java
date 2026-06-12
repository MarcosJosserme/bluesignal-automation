package org.bluesignal.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.bluesignal.core.DriverContext;
import org.bluesignal.data.models.ReportData;
import org.bluesignal.data.models.ReportTestData;
import org.bluesignal.pages.home.HomePage;
import org.bluesignal.pages.report.ReportPage;
import org.bluesignal.utils.JsonDataReader;
import org.openqa.selenium.WebDriver;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReportSteps {

    private WebDriver driver;
    private HomePage homePage;
    private ReportPage reportPage;

    // Datos provenientes de la DataTable.
    private Map<String, String> reportData;

    // Datos provenientes del archivo JSON.
    private ReportData externalReportData;

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

    @When("el visitante completa los datos básicos del reporte")
    public void elVisitanteCompletaLosDatosBasicosDelReporte(
            DataTable dataTable
    ) {

        reportData = dataTable.asMap(
                String.class,
                String.class
        );

        reportPage.form().selectSpecies(
                reportData.get("especie")
        );

        reportPage.form().enterQuantity(
                reportData.get("cantidad")
        );

        reportPage.form().selectEstimatedDistance(
                reportData.get("distancia")
        );

        reportPage.form().enterComment(
                reportData.get("comentario")
        );
    }

    @When("el visitante completa los datos básicos desde el archivo JSON")
    public void elVisitanteCompletaLosDatosBasicosDesdeElArchivoJson() {

        ReportTestData testData = JsonDataReader.read(
                "data/report-data.json",
                ReportTestData.class
        );

        externalReportData = testData.getBasicReport();

        if (externalReportData == null) {
            throw new IllegalStateException(
                    "No se encontró el conjunto basicReport "
                            + "en report-data.json"
            );
        }

        reportPage.form().selectSpecies(
                externalReportData.getSpecies()
        );

        reportPage.form().enterQuantity(
                externalReportData.getQuantity()
        );

        reportPage.form().selectEstimatedDistance(
                externalReportData.getDistance()
        );

        reportPage.form().enterComment(
                externalReportData.getComment()
        );
    }

    @Then("el formulario de reporte se muestra correctamente")
    public void elFormularioDeReporteSeMuestraCorrectamente() {

        assertTrue(
                reportPage.isOpened(),
                "La URL del formulario de reporte "
                        + "no se cargó correctamente"
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
                "El botón de envío no debería habilitarse "
                        + "con el formulario vacío"
        );
    }

    @Then("los datos básicos quedan cargados correctamente")
    public void losDatosBasicosQuedanCargadosCorrectamente() {

        assertEquals(
                reportData.get("especie"),
                reportPage.form().getSelectedSpecies(),
                "La especie seleccionada no coincide"
        );

        assertEquals(
                reportData.get("cantidad"),
                reportPage.form().getQuantity(),
                "La cantidad ingresada no coincide"
        );

        assertEquals(
                reportData.get("distancia"),
                reportPage.form().getSelectedEstimatedDistance(),
                "La distancia seleccionada no coincide"
        );

        assertEquals(
                reportData.get("comentario"),
                reportPage.form().getComment(),
                "El comentario ingresado no coincide"
        );
    }

    @Then("los datos externos quedan cargados correctamente")
    public void losDatosExternosQuedanCargadosCorrectamente() {

        assertEquals(
                externalReportData.getSpecies(),
                reportPage.form().getSelectedSpecies(),
                "La especie obtenida desde JSON no coincide"
        );

        assertEquals(
                externalReportData.getQuantity(),
                reportPage.form().getQuantity(),
                "La cantidad obtenida desde JSON no coincide"
        );

        assertEquals(
                externalReportData.getDistance(),
                reportPage.form().getSelectedEstimatedDistance(),
                "La distancia obtenida desde JSON no coincide"
        );

        assertEquals(
                externalReportData.getComment(),
                reportPage.form().getComment(),
                "El comentario obtenido desde JSON no coincide"
        );
    }
}


