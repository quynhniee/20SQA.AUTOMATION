package org.example.pftesting.pages.suppliers;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ElementsCollection;
import constants.TitleKeys;
import keywords.WebUI;
import org.example.pftesting.pages.CommonPage;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

// page_url = https://quanlycuahangcom.netlify.app/suppliers
public class SupplierManagement extends CommonPage {
    public By pageTitle = By.cssSelector("h1.Polaris-Header-Title");
    public By dataTable = By.cssSelector("table.Polaris-IndexTable__Table");
    public By addSupplierButton = By.cssSelector("button[class*='Polaris-Button--variantPrimary']");
    public By tableHeaderCheckbox = By.cssSelector("table thead th .Polaris-Checkbox");

    public void pageIsLoaded() {
        WebUI.waitForElementVisible(pageTitle);
        assert $(pageTitle).getText().equals(TitleKeys.ORDER_SUPPLIER_PAGE);
        WebUI.waitForElementVisible(dataTable);
        WebUI.waitForElementVisible(addSupplierButton);
        $(dataTable).$$("tbody tr").shouldBe(CollectionCondition.sizeGreaterThanOrEqual(1), Duration.ofSeconds(10));
    }

    public void addSupplierButtonPerformsCorrectly() {
        WebUI.clickElement(addSupplierButton);
        WebUI.waitForModalWithTitle(TitleKeys.ADD_SUPPLIER_MODAL);
        WebUI.clickElement(By.xpath("//button[@aria-label='Close']"));
        WebUI.assertModalWithTitleIsClosed(TitleKeys.ADD_SUPPLIER_MODAL);
    }

    public void tableHeaderCheckboxPerformsSelectAllCorrectly() {
        if (!WebUI.areAllCheckboxesUnchecked()) {
            WebUI.unselectAllCheckboxes();
        }
        WebUI.clickElement(tableHeaderCheckbox);
        assert WebUI.areAllCheckboxesChecked();
    }

    public void tableHeaderCheckboxPerformsUnselectAllCorrectly() {
        if (!WebUI.areAllCheckboxesChecked()) {
            WebUI.selectAllCheckboxes();
        }
        WebUI.clickElement(tableHeaderCheckbox);
        assert WebUI.areAllCheckboxesUnchecked();
    }
}