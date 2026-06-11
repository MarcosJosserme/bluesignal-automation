package org.bluesignal.tests;

import org.bluesignal.core.BaseTest;
import org.bluesignal.pages.home.HomePage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomeTest extends BaseTest {

    @Test
    public void shouldOpenBlueSignalHomePage() {

        HomePage homePage = new HomePage(driver);

        assertTrue(homePage.isLoaded());
    }

    @Test
    public void shouldDisplayMainMap() {

        HomePage homePage = new HomePage(driver);

        assertTrue(homePage.isLoaded());

homePage.map().scrollIntoView();

assertTrue(homePage.map().isVisible());
assertTrue(homePage.map().isCanvasVisible());
    }

    @Test
    public void shouldSelectLocality() {

        HomePage homePage = new HomePage(driver);

        assertTrue(homePage.isLoaded());

        homePage.localitySelector().open();
        homePage.localitySelector().selectLocality("Mar del Plata");

        assertEquals(
                "Mar del Plata",
                homePage.localitySelector().getSelectedLocality()
        );
    }

    public void shouldDisplayNavbarElements() {

    HomePage homePage = new HomePage(driver);

    assertTrue(homePage.navbar().isVisible());
    assertTrue(homePage.navbar().isLogoVisible());
    assertTrue(homePage.navbar().isHomeLinkVisible());
    assertTrue(homePage.navbar().isReportButtonVisible());
    assertTrue(homePage.navbar().isProfileLoginButtonVisible());
    assertTrue(homePage.navbar().isPushNotificationButtonVisible());
    assertFalse(homePage.navbar().isPushNotificationButtonEnabled());
    assertTrue(homePage.navbar().isThemeToggleButtonVisible());
    }
}