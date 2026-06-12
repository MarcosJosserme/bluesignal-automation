package org.bluesignal.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.bluesignal.core.DriverContext;
import org.bluesignal.pages.home.HomePage;
import org.bluesignal.pages.report.ReportPage;
import org.openqa.selenium.WebDriver;
import io.cucumber.datatable.DataTable;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;



public class ReportSteps {

    private WebDriver driver;
    private HomePage homePage;
    private ReportPage reportPage;
    private Map<String, String> reportData;

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

    @When("el visitante completa los datos básicos del reporte")
    public void elVisitanteCompletaLosDatosBasicosDelReporte(
        DataTable dataTable
    ) {

    reportData = dataTable.asMap(String.class, String.class);

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
}
