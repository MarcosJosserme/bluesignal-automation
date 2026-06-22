package org.bluesignal.steps;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;

import org.bluesignal.core.DriverContext;
import org.bluesignal.data.models.ReportData;
import org.bluesignal.data.models.ReportTestData;
import org.bluesignal.pages.home.HomePage;
import org.bluesignal.pages.report.ReportPage;
import org.bluesignal.utils.JsonDataReader;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;


import java.io.ByteArrayInputStream;
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

        configurarMetadataReporte(
                "Selección de localidad para reporte",
                "Validar que el usuario pueda seleccionar Mar del Plata como localidad antes de acceder al formulario de reporte.",
                "critical"
        );

        Allure.step("Obtener el driver activo e inicializar la Home Page", () -> {
            driver = DriverContext.getDriver();
            homePage = new HomePage(driver);
        });

        Allure.step("Abrir el selector de localidades", () -> {
            homePage.localitySelector().open();
        });

        Allure.step("Seleccionar Mar del Plata como localidad", () -> {
            homePage.localitySelector().selectLocality("Mar del Plata");
        });

        Allure.step("Esperar que cargue la ruta de Mar del Plata", () -> {
            homePage.localitySelector()
                    .waitUntilLocalityRouteIsLoaded("mar-del-plata");
        });
    }

    @When("accede al formulario de reporte")
    public void accedeAlFormularioDeReporte() {

        configurarMetadataReporte(
                "Acceso al formulario de reporte",
                "Validar que el usuario pueda acceder correctamente al formulario de reporte desde la navegación principal de BlueSignal.",
                "critical"
        );

        Allure.step("Inicializar páginas si todavía no fueron creadas", () -> {
            if (driver == null) {
                driver = DriverContext.getDriver();
            }

            if (homePage == null) {
                homePage = new HomePage(driver);
            }
        });

        Allure.step("Acceder al formulario de reporte desde la barra de navegación", () -> {
            homePage.navbar().goToReport();
            reportPage = new ReportPage(driver);
        });

        adjuntarCapturaSiEsPosible();
    }

    @When("el visitante completa los datos básicos del reporte")
    public void elVisitanteCompletaLosDatosBasicosDelReporte(
            DataTable dataTable
    ) {

        configurarMetadataReporte(
                "Carga parcial del formulario con datos estructurados",
                "Validar que el visitante pueda completar los datos básicos del formulario utilizando datos provenientes de una DataTable.",
                "normal"
        );

        Allure.step("Convertir la DataTable en un mapa de datos de reporte", () -> {
            reportData = dataTable.asMap(
                    String.class,
                    String.class
            );
        });

        Allure.step("Seleccionar especie del reporte", () -> {
            reportPage.form().selectSpecies(
                    reportData.get("especie")
            );
        });

        Allure.step("Ingresar cantidad de animales observados", () -> {
            reportPage.form().enterQuantity(
                    reportData.get("cantidad")
            );
        });

        Allure.step("Seleccionar distancia estimada", () -> {
            reportPage.form().selectEstimatedDistance(
                    reportData.get("distancia")
            );
        });

        Allure.step("Ingresar comentario del reporte", () -> {
            reportPage.form().enterComment(
                    reportData.get("comentario")
            );
        });

        adjuntarCapturaSiEsPosible();
    }

    @When("el visitante completa los datos básicos desde el archivo JSON")
    public void elVisitanteCompletaLosDatosBasicosDesdeElArchivoJson() {

        configurarMetadataReporte(
                "Carga parcial del formulario con datos externos",
                "Validar que el visitante pueda completar los datos básicos del formulario utilizando información proveniente de un archivo JSON.",
                "normal"
        );

        Allure.step("Leer datos de prueba desde report-data.json", () -> {
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
        });

        Allure.step("Seleccionar especie obtenida desde JSON", () -> {
            reportPage.form().selectSpecies(
                    externalReportData.getSpecies()
            );
        });

        Allure.step("Ingresar cantidad obtenida desde JSON", () -> {
            reportPage.form().enterQuantity(
                    externalReportData.getQuantity()
            );
        });

        Allure.step("Seleccionar distancia obtenida desde JSON", () -> {
            reportPage.form().selectEstimatedDistance(
                    externalReportData.getDistance()
            );
        });

        Allure.step("Ingresar comentario obtenido desde JSON", () -> {
            reportPage.form().enterComment(
                    externalReportData.getComment()
            );
        });

        adjuntarCapturaSiEsPosible();
    }

    @Then("el formulario de reporte se muestra correctamente")
    public void elFormularioDeReporteSeMuestraCorrectamente() {

        configurarMetadataReporte(
                "Acceso al formulario de reporte",
                "Validar que el formulario de reporte se abra correctamente y sea visible para el usuario.",
                "critical"
        );

        Allure.step("Validar que la URL del formulario de reporte se cargó correctamente", () -> {
            assertTrue(
                    reportPage.isOpened(),
                    "La URL del formulario de reporte "
                            + "no se cargó correctamente"
            );
        });

        Allure.step("Validar que el formulario de reporte esté visible", () -> {
            assertTrue(
                    reportPage.form().isVisible(),
                    "El formulario de reporte no está visible"
            );
        });

        adjuntarCapturaSiEsPosible();
    }

    @Then("el botón de envío del reporte permanece deshabilitado")
    public void elBotonDeEnvioDelReportePermaneceDeshabilitado() {

        configurarMetadataReporte(
                "Formulario vacío sin posibilidad de envío",
                "Validar que el botón de envío permanezca deshabilitado cuando el formulario obligatorio no fue completado.",
                "critical"
        );

        Allure.step("Validar que la página de reporte se abrió correctamente", () -> {
            assertTrue(
                    reportPage.isOpened(),
                    "La página de reporte no se abrió correctamente"
            );
        });

        Allure.step("Validar que el botón de envío permanezca deshabilitado", () -> {
            assertFalse(
                    reportPage.form().isSubmitButtonEnabled(),
                    "El botón de envío no debería habilitarse "
                            + "con el formulario vacío"
            );
        });

        adjuntarCapturaSiEsPosible();
    }

    @Then("los datos básicos quedan cargados correctamente")
    public void losDatosBasicosQuedanCargadosCorrectamente() {

        configurarMetadataReporte(
                "Validación de datos estructurados cargados en formulario",
                "Validar que los datos ingresados desde una DataTable queden correctamente cargados en el formulario de reporte.",
                "normal"
        );

        Allure.step("Validar especie seleccionada", () -> {
            assertEquals(
                    reportData.get("especie"),
                    reportPage.form().getSelectedSpecies(),
                    "La especie seleccionada no coincide"
            );
        });

        Allure.step("Validar cantidad ingresada", () -> {
            assertEquals(
                    reportData.get("cantidad"),
                    reportPage.form().getQuantity(),
                    "La cantidad ingresada no coincide"
            );
        });

        Allure.step("Validar distancia estimada seleccionada", () -> {
            assertEquals(
                    reportData.get("distancia"),
                    reportPage.form().getSelectedEstimatedDistance(),
                    "La distancia seleccionada no coincide"
            );
        });

        Allure.step("Validar comentario ingresado", () -> {
            assertEquals(
                    reportData.get("comentario"),
                    reportPage.form().getComment(),
                    "El comentario ingresado no coincide"
            );
        });

        adjuntarCapturaSiEsPosible();
    }

    @Then("los datos externos quedan cargados correctamente")
    public void losDatosExternosQuedanCargadosCorrectamente() {

        configurarMetadataReporte(
                "Validación de datos externos cargados en formulario",
                "Validar que los datos obtenidos desde un archivo JSON queden correctamente cargados en el formulario de reporte.",
                "normal"
        );

        Allure.step("Validar especie obtenida desde JSON", () -> {
            assertEquals(
                    externalReportData.getSpecies(),
                    reportPage.form().getSelectedSpecies(),
                    "La especie obtenida desde JSON no coincide"
            );
        });

        Allure.step("Validar cantidad obtenida desde JSON", () -> {
            assertEquals(
                    externalReportData.getQuantity(),
                    reportPage.form().getQuantity(),
                    "La cantidad obtenida desde JSON no coincide"
            );
        });

        Allure.step("Validar distancia obtenida desde JSON", () -> {
            assertEquals(
                    externalReportData.getDistance(),
                    reportPage.form().getSelectedEstimatedDistance(),
                    "La distancia obtenida desde JSON no coincide"
            );
        });

        Allure.step("Validar comentario obtenido desde JSON", () -> {
            assertEquals(
                    externalReportData.getComment(),
                    reportPage.form().getComment(),
                    "El comentario obtenido desde JSON no coincide"
            );
        });

        adjuntarCapturaSiEsPosible();
    }

    private void configurarMetadataReporte(
            String story,
            String description,
            String severity
    ) {
        Allure.label("epic", "BlueSignal");
        Allure.label("feature", "Reporte de avistajes");
        Allure.label("story", story);
        Allure.label("severity", severity);
        Allure.description(description);
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
