package org.bluesignal.pages.home.components;

import org.bluesignal.core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MapComponent extends BasePage {

    @FindBy(id = "dashboard-map")
    private WebElement mapContainer;

    @FindBy(css = ".maplibregl-canvas")
    private WebElement mapCanvas;

    public MapComponent(WebDriver driver) {
        super(driver);
    }

    public boolean isVisible() {
        return isDisplayed(mapContainer);
    }

    public boolean isCanvasVisible() {
        return isDisplayed(mapCanvas);
    }
}
