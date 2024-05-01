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

    @Step("Select a random checkbox from the table")
    public void selectRandomCheckbox() {
        ElementsCollection checkboxes = $$("tbody tr .Polaris-Checkbox");
        int randomIndex = new Random().nextInt(checkboxes.size());
        SelenideElement randomCheckbox = checkboxes.get(randomIndex);
        if (!randomCheckbox.findElement(By.cssSelector("input[type=checkbox]")).isSelected()) {
            randomCheckbox.click();
        }
    }

    @Step("Select random 2 checkboxes from the table")
    public void selectRandom2Checkboxes() {
        ElementsCollection checkboxes = $$("tbody tr .Polaris-Checkbox");
        int randomIndex1 = new Random().nextInt(checkboxes.size());
        int randomIndex2 = new Random().nextInt(checkboxes.size());
        while (randomIndex2 == randomIndex1) {
            randomIndex2 = new Random().nextInt(checkboxes.size());
        }
        SelenideElement randomCheckbox1 = checkboxes.get(randomIndex1);
        SelenideElement randomCheckbox2 = checkboxes.get(randomIndex2);
        if (!randomCheckbox1.findElement(By.cssSelector("input[type=checkbox]")).isSelected()) {
            randomCheckbox1.click();
        }
        if (!randomCheckbox2.findElement(By.cssSelector("input[type=checkbox]")).isSelected()) {
            randomCheckbox2.click();
        }
    }

    @Step("Select all checkboxes from the table")
    public void selectAllCheckboxes() {
        ElementsCollection checkboxes = $$("tbody tr .Polaris-Checkbox");
        for (SelenideElement checkbox : checkboxes) {
            if (!checkbox.findElement(By.cssSelector("input[type=checkbox]")).isSelected()) {
                checkbox.click();
            }
        }
    }

    @Step("Unselect all checkboxes from the table")
    public void unselectAllCheckboxes() {
        ElementsCollection checkboxes = $$("tbody tr .Polaris-Checkbox");
        for (SelenideElement checkbox : checkboxes) {
            if (checkbox.findElement(By.cssSelector("input[type=checkbox]")).isSelected()) {
                checkbox.click();
            }
        }
    }

    @Step("Make sure all checkboxes are checked")
    public boolean areAllCheckboxesChecked() {
        ElementsCollection checkboxes = $$("tbody tr input[type='checkbox']");
        for (SelenideElement checkbox : checkboxes) {
            if (!checkbox.isSelected()) {
                return false;
            }
        }
        return true;
    }

    @Step("Make sure all checkboxes are unchecked")
    public boolean areAllCheckboxesUnchecked() {
        ElementsCollection checkboxes = $$("tbody tr input[type='checkbox']");
        for (SelenideElement checkbox : checkboxes) {
            if (checkbox.isSelected()) {
                return false;
            }
        }
        return true;
    }

    public void unSelectButtonPerformsCorrectly() {
        WebUI.ensureButtonIsDisabledByClass(unSelectButton);
        selectAllCheckboxes();
        WebUI.ensureButtonIsNotDisabledByClass(unSelectButton);
        WebUI.clickElement(unSelectButton);
        assert areAllCheckboxesUnchecked();
        WebUI.ensureButtonIsDisabledByClass(unSelectButton);
    }

    @Step("Bulk action buttons are visible")
    public void bulkActionsIsVisible() {
        selectAllCheckboxes();
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
        selectRandomCheckbox();
        bulkActionsIsVisible();
        selectRandom2Checkboxes();
        WebUI.ensureButtonIsDisabledByClass(editOrderButton);
        WebUI.ensureButtonIsNotDisabledByClass(deleteOrderButton);
        WebUI.ensureButtonIsNotDisabledByClass(confirmPaymentButton);
        WebUI.clickElement(unSelectButton);
        filterPaidOrders();
        selectRandomCheckbox();
        WebUI.ensureButtonIsDisabledByClass(confirmPaymentButton);
        WebUI.ensureButtonIsNotDisabledByClass(editOrderButton);
        WebUI.ensureButtonIsNotDisabledByClass(deleteOrderButton);
        WebUI.clickElement(unSelectButton);
    }
}