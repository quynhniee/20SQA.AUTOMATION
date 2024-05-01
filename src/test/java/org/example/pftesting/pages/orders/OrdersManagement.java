package org.example.pftesting.pages.orders;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Step;
import keywords.WebUI;
import org.openqa.selenium.By;

import java.time.Duration;
import java.util.Random;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

// page_url = https://quanlycuahangcom.netlify.app/orders
public class OrdersManagement {
    public By dataTable = By.cssSelector("table.Polaris-DataTable__Table");
    
    public By unSelectButton = By.id("cancel--select--button");
    
    public By statusFilter = By.id("active--popover--button");

    public By paginationNextButton = By.id("next--page--button");

    public By paginationPrevButton = By.id("previous--page--button");

    public By editOrderButton = By.id("edit--order--button");

    public By deleteOrderButton = By.id("delete--order--button");

    public By confirmPaymentButton = By.id("confirm--payment--order--button");

    public void pageIsLoaded() {
        WebUI.waitForElementVisible(dataTable);
        WebUI.waitForElementVisible(unSelectButton);
        WebUI.waitForElementVisible(paginationNextButton);
        WebUI.waitForElementVisible(paginationPrevButton);
        $(dataTable).$$("tbody tr").shouldBe(CollectionCondition.sizeGreaterThanOrEqual(1), Duration.ofSeconds(10));
        WebUI.waitForElementVisible(statusFilter);
    }


    public void unSelectButtonPerformsCorrectly() {
        WebUI.ensureButtonIsDisabledByClass(unSelectButton);
        WebUI.selectAllCheckboxes();
        WebUI.ensureButtonIsNotDisabledByClass(unSelectButton);
        WebUI.clickElement(unSelectButton);
        assert WebUI.areAllCheckboxesUnchecked();
        WebUI.ensureButtonIsDisabledByClass(unSelectButton);
    }

    @Step("Bulk action buttons are visible")
    public void bulkActionsIsVisible() {
        WebUI.selectAllCheckboxes();
        WebUI.waitForElementVisible(editOrderButton);
        WebUI.waitForElementVisible(deleteOrderButton);
        WebUI.waitForElementVisible(confirmPaymentButton);
    }

    @Step("Filter paid orders")
    public void filterPaidOrders() {
        WebUI.clickElement(statusFilter);
        WebUI.selectOptionInPopoverByLabel("Đã thanh toán");
        $x("//tr//*[contains(text(),'Đã thanh toán')]").shouldBe(Condition.visible);
    }

    public void bulkActionsPerformCorrectly() {
        WebUI.selectRandomCheckbox();
        bulkActionsIsVisible();
        WebUI.selectRandom2Checkboxes();
        WebUI.ensureButtonIsDisabledByClass(editOrderButton);
        WebUI.ensureButtonIsNotDisabledByClass(deleteOrderButton);
        WebUI.ensureButtonIsNotDisabledByClass(confirmPaymentButton);
        WebUI.clickElement(unSelectButton);
        filterPaidOrders();
        WebUI.selectRandomCheckbox();
        WebUI.ensureButtonIsDisabledByClass(confirmPaymentButton);
        WebUI.ensureButtonIsNotDisabledByClass(editOrderButton);
        WebUI.ensureButtonIsNotDisabledByClass(deleteOrderButton);
        WebUI.clickElement(unSelectButton);
    }
}