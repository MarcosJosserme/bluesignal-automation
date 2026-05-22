package org.bluesignal.pages.home.components;

import org.bluesignal.core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LocalitySelectorComponent extends BasePage {

    @FindBy(id = "locality-manual-btn")
    private WebElement localityButton;

    @FindBy(id = "locality-select-empty")
    private WebElement emptyLocalitySelect;

    @FindBy(id = "locality-select")
    private WebElement localitySelect;

    public LocalitySelectorComponent(WebDriver driver) {
        super(driver);
    }

    public void open() {
        click(localityButton);
    }

    public void selectLocality(String localityName) {
        selectByVisibleText(emptyLocalitySelect, localityName);
    }

     public void waitUntilLocalityRouteIsLoaded(String localitySlug) {
        waitUntilUrlContains("/localidad/" + localitySlug + "/");
    }

    public String getSelectedLocality() {
        return getSelectedOptionText(localitySelect);
    }
}
