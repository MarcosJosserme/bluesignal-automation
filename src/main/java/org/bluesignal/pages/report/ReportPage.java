package org.bluesignal.pages.report;

import org.bluesignal.core.BasePage;
import org.bluesignal.pages.report.components.ReportFormComponent;
import org.openqa.selenium.WebDriver;

public class ReportPage extends BasePage {

    private final ReportFormComponent form;

    public ReportPage(WebDriver driver) {
        super(driver);
        this.form = new ReportFormComponent(driver);
    }

    public boolean isOpened() {
        return currentUrlContains("/reportar/");
    }

    public ReportFormComponent form() {
        return form;
    }
}
