package org.bluesignal.tests;

import org.bluesignal.core.BaseTest;
import org.bluesignal.pages.home.HomePage;
import org.bluesignal.pages.report.ReportPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReportTest extends BaseTest {

    @Test
    public void shouldOpenReportFormDesktop() {

        HomePage homePage = new HomePage(driver);

        homePage.localitySelector().open();
        homePage.localitySelector().selectLocality("Mar del Plata");
        homePage.localitySelector().waitUntilLocalityRouteIsLoaded("mar-del-plata");

        homePage.navbar().goToReport();

        ReportPage reportPage = new ReportPage(driver);

        assertTrue(reportPage.isOpened());
        assertTrue(reportPage.form().isVisible());
    }

    @Test
    public void shouldKeepSubmitButtonDisabledWhenFormIsEmpty() {

        HomePage homePage = new HomePage(driver);

        homePage.localitySelector().open();
        homePage.localitySelector().selectLocality("Mar del Plata");
        homePage.localitySelector().waitUntilLocalityRouteIsLoaded("mar-del-plata");

        homePage.navbar().goToReport();

        ReportPage reportPage = new ReportPage(driver);

        assertTrue(reportPage.isOpened());
        assertFalse(reportPage.form().isSubmitButtonEnabled());
    }
}