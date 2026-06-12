package org.bluesignal.utils;

import org.bluesignal.data.models.ReportData;
import org.bluesignal.data.models.ReportTestData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JsonDataReaderTest {

    @Test
    public void shouldReadBasicReportDataFromJson() {

        ReportTestData testData = JsonDataReader.read(
                "data/report-data.json",
                ReportTestData.class
        );

        assertNotNull(
                testData,
                "El archivo JSON debería convertirse en un objeto"
        );

        ReportData basicReport = testData.getBasicReport();

        assertNotNull(
                basicReport,
                "El conjunto basicReport debería existir"
        );

        assertEquals(
                "Ballena Franca Austral",
                basicReport.getSpecies()
        );

        assertEquals(
                "2",
                basicReport.getQuantity()
        );

        assertEquals(
                "En el mar: cerca (< 1 km)",
                basicReport.getDistance()
        );

        assertEquals(
                "Avistaje automatizado de QA",
                basicReport.getComment()
        );
    }
}