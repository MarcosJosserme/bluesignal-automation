package org.bluesignal.pages.home;

import org.bluesignal.components.NavbarComponent;
import org.bluesignal.core.BasePage;
import org.bluesignal.pages.home.components.LocalitySelectorComponent;
import org.bluesignal.pages.home.components.MapComponent;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private final MapComponent map;
    private final LocalitySelectorComponent localitySelector;
    private final NavbarComponent navbar;

    public HomePage(WebDriver driver) {
        super(driver);
        this.map = new MapComponent(driver);
        this.localitySelector = new LocalitySelectorComponent(driver);
        this.navbar = new NavbarComponent(driver);
    }

    public boolean isLoaded() {
        return getTitle().contains("BlueSignal");
    }

    public MapComponent map() {
        return map;
    }

    public LocalitySelectorComponent localitySelector() {
        return localitySelector;
    }
    
    public NavbarComponent navbar() {
        return navbar;
    }

}