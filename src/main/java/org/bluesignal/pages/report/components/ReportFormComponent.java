package org.bluesignal.pages.report.components;

import org.bluesignal.core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ReportFormComponent extends BasePage {

    @FindBy(tagName = "form")
    private WebElement form;

    @FindBy(id = "id_especie")
    private WebElement speciesSelect;

    @FindBy(id = "id_cantidad_individuos")
    private WebElement quantityInput;

    @FindBy(id = "id_distancia_estimada")
    private WebElement estimatedDistanceSelect;

    @FindBy(id = "id_estado_mar")
    private WebElement seaStateSelect;

    @FindBy(id = "id_condicion_cielo")
    private WebElement skyConditionSelect;

    @FindBy(id = "id_comentario")
    private WebElement commentTextArea;

    @FindBy(id = "report-submit-btn")
    private WebElement submitButton;

    public ReportFormComponent(WebDriver driver) {
        super(driver);
    }

    public boolean isVisible() {
        return isDisplayed(form);
    }

    public void selectSpecies(String species) {
        selectByVisibleText(speciesSelect, species);
    }

    public String getSelectedSpecies() {
        return getSelectedOptionText(speciesSelect);
    }

    public void enterQuantity(String quantity) {
        type(quantityInput, quantity);
    }

    public String getQuantity() {
        return getValue(quantityInput);
    }

    public void selectEstimatedDistance(String distance) {
        scrollTo(estimatedDistanceSelect);
        selectByVisibleText(estimatedDistanceSelect, distance);
    }

    public String getSelectedEstimatedDistance() {
        return getSelectedOptionText(estimatedDistanceSelect);
    }

    public void selectSeaState(String seaState) {
        scrollTo(seaStateSelect);
        selectByVisibleText(seaStateSelect, seaState);
    }

    public String getSelectedSeaState() {
        return getSelectedOptionText(seaStateSelect);
    }

    public void selectSkyCondition(String skyCondition) {
        scrollTo(skyConditionSelect);
        selectByVisibleText(skyConditionSelect, skyCondition);
    }

    public String getSelectedSkyCondition() {
        return getSelectedOptionText(skyConditionSelect);
    }

    public void enterComment(String comment) {
        scrollTo(commentTextArea);
        type(commentTextArea, comment);
    }

    public String getComment() {
        return getValue(commentTextArea);
    }

    public boolean isSubmitButtonEnabled() {
        return isEnabled(submitButton);
    }
}