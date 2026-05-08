package org.bluesignal.tests;

import org.bluesignal.core.BaseTest;
import org.bluesignal.pages.HomePage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomeTest extends BaseTest {

    @Test
    public void shouldOpenBlueSignalHomePage() {

        HomePage homePage = new HomePage(driver);

        assertTrue(homePage.isLoaded());
    }
}