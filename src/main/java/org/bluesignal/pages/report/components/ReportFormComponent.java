package org.bluesignal.pages.report.components;

import org.bluesignal.core.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ReportFormComponent extends BasePage {

    @FindBy(tagName = "form")
    private WebElement form;

    @FindBy(id = "report-submit-btn")
    private WebElement submitButton;

    public ReportFormComponent(WebDriver driver) {
        super(driver);
    }

    public boolean isVisible() {
        return isDisplayed(form);
    }

    public boolean isSubmitButtonEnabled() {
        return isEnabled(submitButton);
    }
}
