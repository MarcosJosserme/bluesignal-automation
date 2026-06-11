package org.bluesignal.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class HomeSteps {

    @Given("el visitante no se encuentra autenticado")
    public void elVisitanteNoSeEncuentraAutenticado() {
        System.out.println("El visitante accede como usuario no autenticado");
    }

    @When("accede a la página principal de BlueSignal")
    public void accedeALaPaginaPrincipalDeBlueSignal() {
        System.out.println("El visitante accede a la página principal");
    }

    @Then("la página principal se muestra correctamente")
    public void laPaginaPrincipalSeMuestraCorrectamente() {
        System.out.println("La página principal se muestra correctamente");
    }
}