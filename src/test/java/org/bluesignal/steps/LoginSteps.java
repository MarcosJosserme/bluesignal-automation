package org.bluesignal.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.bluesignal.core.DriverContext;
import org.bluesignal.pages.login.LoginPage;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginSteps {

    private WebDriver driver;
    private LoginPage loginPage;

    @Given("el visitante accede a la página de inicio de sesión")
    public void elVisitanteAccedeALaPaginaDeInicioDeSesion() {

        driver = DriverContext.getDriver();

        loginPage = new LoginPage(driver).open();
    }

    @When("el visitante ingresa credenciales inválidas")
    public void elVisitanteIngresaCredencialesInvalidas() {

        loginPage.form().login(
                "invalid_user_qa@example.com",
                "invalidPassword123"
        );
    }

    @Then("la página de inicio de sesión se muestra correctamente")
    public void laPaginaDeInicioDeSesionSeMuestraCorrectamente() {

        assertTrue(
                loginPage.isOpened(),
                "La URL de inicio de sesión no se cargó correctamente"
        );

        assertTrue(
                loginPage.form().isVisible(),
                "El formulario de inicio de sesión no está visible"
        );
    }

    @Then("el formulario de inicio de sesión muestra todos sus controles")
    public void elFormularioDeInicioDeSesionMuestraTodosSusControles() {

        assertTrue(
                loginPage.form().isEmailInputVisible(),
                "El campo de email no está visible"
        );

        assertTrue(
                loginPage.form().isPasswordInputVisible(),
                "El campo de contraseña no está visible"
        );

        assertTrue(
                loginPage.form().isSubmitButtonVisible(),
                "El botón de inicio de sesión no está visible"
        );

        assertTrue(
                loginPage.form().isCreateProfileLinkVisible(),
                "El enlace para crear un perfil no está visible"
        );

        assertTrue(
                loginPage.form().isForgotPasswordLinkVisible(),
                "El enlace para recuperar la contraseña no está visible"
        );
    }

    @Then("permanece en la página de inicio de sesión")
    public void permaneceEnLaPaginaDeInicioDeSesion() {

        assertTrue(
                loginPage.isOpened(),
                "El visitante no debería abandonar la página de inicio de sesión"
        );
    }

    @Then("visualiza un mensaje de error de autenticación")
    public void visualizaUnMensajeDeErrorDeAutenticacion() {

        assertTrue(
                loginPage.form().isErrorMessageVisible(),
                "No se mostró el mensaje de credenciales inválidas"
        );
    }
}
