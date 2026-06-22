package org.bluesignal.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import org.bluesignal.core.DriverContext;
import org.bluesignal.pages.login.LoginPage;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginSteps {

    private WebDriver driver;
    private LoginPage loginPage;

    @Given("el visitante accede a la página de inicio de sesión")
    public void elVisitanteAccedeALaPaginaDeInicioDeSesion() {

        configurarMetadataLogin(
                "Acceso a la página de inicio de sesión",
                "Validar que el visitante pueda acceder correctamente a la página de inicio de sesión de BlueSignal.",
                "critical"
        );

        Allure.step("Obtener el driver activo", () -> {
            driver = DriverContext.getDriver();
        });

        Allure.step("Abrir la página de inicio de sesión", () -> {
            loginPage = new LoginPage(driver).open();
        });

        adjuntarCapturaSiEsPosible();
    }

    @When("el visitante ingresa credenciales inválidas")
    public void elVisitanteIngresaCredencialesInvalidas() {

        configurarMetadataLogin(
                "Inicio de sesión con credenciales inválidas",
                "Validar que el sistema rechace credenciales inválidas y mantenga al visitante en la página de inicio de sesión.",
                "critical"
        );

        Allure.step("Ingresar email y contraseña inválidos", () -> {
            loginPage.form().login(
                    "invalid_user_qa@example.com",
                    "invalidPassword123"
            );
        });

        adjuntarCapturaSiEsPosible();
    }

    @Then("la página de inicio de sesión se muestra correctamente")
    public void laPaginaDeInicioDeSesionSeMuestraCorrectamente() {

        configurarMetadataLogin(
                "Visualización de la página de inicio de sesión",
                "Validar que la URL de inicio de sesión cargue correctamente y que el formulario sea visible.",
                "normal"
        );

        Allure.step("Validar que la URL de inicio de sesión se cargó correctamente", () -> {
            assertTrue(
                    loginPage.isOpened(),
                    "La URL de inicio de sesión no se cargó correctamente"
            );
        });

        Allure.step("Validar que el formulario de inicio de sesión esté visible", () -> {
            assertTrue(
                    loginPage.form().isVisible(),
                    "El formulario de inicio de sesión no está visible"
            );
        });

        adjuntarCapturaSiEsPosible();
    }

    @Then("el formulario de inicio de sesión muestra todos sus controles")
    public void elFormularioDeInicioDeSesionMuestraTodosSusControles() {

        configurarMetadataLogin(
                "Visualización de controles del formulario de inicio de sesión",
                "Validar que el formulario de inicio de sesión muestre los campos y enlaces requeridos para el usuario.",
                "normal"
        );

        Allure.step("Validar visibilidad del campo de email", () -> {
            assertTrue(
                    loginPage.form().isEmailInputVisible(),
                    "El campo de email no está visible"
            );
        });

        Allure.step("Validar visibilidad del campo de contraseña", () -> {
            assertTrue(
                    loginPage.form().isPasswordInputVisible(),
                    "El campo de contraseña no está visible"
            );
        });

        Allure.step("Validar visibilidad del botón de inicio de sesión", () -> {
            assertTrue(
                    loginPage.form().isSubmitButtonVisible(),
                    "El botón de inicio de sesión no está visible"
            );
        });

        Allure.step("Validar visibilidad del enlace para crear perfil", () -> {
            assertTrue(
                    loginPage.form().isCreateProfileLinkVisible(),
                    "El enlace para crear un perfil no está visible"
            );
        });

        Allure.step("Validar visibilidad del enlace para recuperar contraseña", () -> {
            assertTrue(
                    loginPage.form().isForgotPasswordLinkVisible(),
                    "El enlace para recuperar la contraseña no está visible"
            );
        });

        adjuntarCapturaSiEsPosible();
    }

    @Then("permanece en la página de inicio de sesión")
    public void permaneceEnLaPaginaDeInicioDeSesion() {

        configurarMetadataLogin(
                "Permanencia en login ante credenciales inválidas",
                "Validar que el visitante no abandone la página de inicio de sesión luego de ingresar credenciales inválidas.",
                "critical"
        );

        Allure.step("Validar que el visitante permanezca en la página de inicio de sesión", () -> {
            assertTrue(
                    loginPage.isOpened(),
                    "El visitante no debería abandonar la página de inicio de sesión"
            );
        });

        adjuntarCapturaSiEsPosible();
    }

    @Then("visualiza un mensaje de error de autenticación")
    public void visualizaUnMensajeDeErrorDeAutenticacion() {

        configurarMetadataLogin(
                "Mensaje de error por credenciales inválidas",
                "Validar que el sistema muestre un mensaje de error cuando el visitante ingresa credenciales inválidas.",
                "critical"
        );

        Allure.step("Validar visibilidad del mensaje de error de autenticación", () -> {
            assertTrue(
                    loginPage.form().isErrorMessageVisible(),
                    "No se mostró el mensaje de credenciales inválidas"
            );
        });

        adjuntarCapturaSiEsPosible();
    }

    private void configurarMetadataLogin(
            String story,
            String description,
            String severity
    ) {
        Allure.label("epic", "BlueSignal");
        Allure.label("feature", "Inicio de sesión");
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