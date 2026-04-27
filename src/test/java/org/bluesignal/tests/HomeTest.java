package org.bluesignal.tests;

import org.bluesignal.core.BaseTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomeTest extends BaseTest {

    @Test
    public void shouldOpenBlueSignalHomePage() {

        String title = driver.getTitle();

        assertTrue(title.contains("BlueSignal"));
    }
}
