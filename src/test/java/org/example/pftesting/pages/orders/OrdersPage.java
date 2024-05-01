package org.example.pftesting.pages.orders;

import com.codeborne.selenide.SelenideElement;
import constants.ErrorKeys;
import io.qameta.allure.Description;
import keywords.WebUI;
import org.example.pftesting.pages.CommonPage;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;

import static com.codeborne.selenide.Selenide.*;
import static utils.CodeGenerator.generateCode;

// page_url = https://quanlycuahangcom.netlify.app/orders
public class OrdersPage extends CommonPage {
    public By orderCodeInput = By.id("order--code");
    public By orderSupplierSelect = By.id("order--supplier--select");
    public By orderNoteInput = By.id("order--note");
    public By addOrderProductButton = By.id("add-order-product-btn");
    public By productQuantityInput = By.id("quantity--input");
    public By productPriceInput = By.id("price--input");
    public By addOrderProductModalBy = By.xpath("//div[contains(@class, 'mall')]");
    public By selectCategory = By.id("select--category");
    public By selectProduct = By.id("select--product");
    public SelenideElement addOrderProductModal;
    public SelenideElement createOrderModal;

    @BeforeClass
    public void setup() {
        open("https://quanlycuahangcom.netlify.app/orders");
    }

    public void openCreateOrderModal() {
        WebUI.clickButtonWithText("Tạo đơn hàng");
        createOrderModal = WebUI.waitForModalWithTitle("Tạo đơn nhập hàng");
    }

    public void closeCreateOrderModal() {
        WebUI.clickButtonInModal(createOrderModal, "Hủy");
        WebUI.assertModalIsClosed(createOrderModal);
    }

    public void createOrder_blankData() {
        WebUI.clickButtonWithText("Lưu");
        WebUI.assertInputHasErrorText("order--code", ErrorKeys.ORDER_CODE_REQUIRED);
        WebUI.assertInputHasErrorText("order--supplier--select", ErrorKeys.ORDER_SUPPLIER_REQUIRED);
        WebUI.assertElementHasText("missing--porudct--error", ErrorKeys.ORDER_PRODUCT_REQUIRED);
    }


    public void createOrder_invalidCode_wrongFormat() {
        WebUI.clearAndFillValue(orderCodeInput, "123");
        WebUI.clickButtonWithText("Lưu");
        WebUI.assertInputHasErrorText("order--code", ErrorKeys.ORDER_CODE_WRONG_FORMAT);
    }

    public void createOrder_invalidCode_containsSpace() {
        WebUI.clearAndFillValue(orderCodeInput, " 123");
        WebUI.clickButtonWithText("Lưu");
        WebUI.assertInputHasErrorText("order--code", ErrorKeys.ORDER_CODE_WRONG_FORMAT);
    }

    public void createOrder_invalidCode_startWithSpace() {
        WebUI.clearAndFillValue(orderCodeInput, "  DH123");
        WebUI.clickButtonWithText("Lưu");
        WebUI.assertInputHasErrorText("order--code", ErrorKeys.ORDER_CODE_WRONG_FORMAT);
    }

    public void createOrder_invalidCode_specialCharacterCode() {
        WebUI.clearAndFillValue(orderCodeInput, "¥¥123");
        WebUI.clickButtonWithText("Lưu");
        WebUI.assertInputHasErrorText("order--code", ErrorKeys.ORDER_CODE_WRONG_FORMAT);
    }

    public void openAddOrderProductModal() {
        WebUI.clickElement(addOrderProductButton);
        addOrderProductModal = WebUI.waitForModalWithTitle("Thêm Mặt Hàng");
    }

    public void confirmAddOrderProductModal() {
        WebUI.clickButtonInModal(addOrderProductModal, "Thêm");
        WebUI.assertModalIsClosed(addOrderProductModal);
    }

    public void closeAddOrderProductModal() {
        WebUI.clickButtonInModal(addOrderProductModal, "Hủy");
        WebUI.assertModalIsClosed(addOrderProductModal);
    }

    public void waitForProductOptionsToLoad() {
        WebUI.waitForOptionsToLoad(selectCategory);
        WebUI.waitForOptionsToLoad(selectProduct);
    }

    @Description("Default values: Số lượng = \"1\", Giá nhập = \"1\"")
    public void addOrderProduct_defaultData() {
        openAddOrderProductModal();
        waitForProductOptionsToLoad();
        WebUI.clickButtonWithText("Thêm");
        WebUI.assertModalIsOpen(addOrderProductModalBy);
        WebUI.assertInputHasErrorText("price--input", ErrorKeys.PRODUCT_PRICE_MIN);
    }

    @Description("Số lượng = \"\", Giá nhập = \"\"")
    public void addOrderProduct_emptyData() {
        openAddOrderProductModal();
        WebUI.clickButtonWithText("Thêm");
        WebUI.assertModalIsOpen(addOrderProductModalBy);
        WebUI.assertInputHasErrorText("price--input", ErrorKeys.PRODUCT_PRICE_MIN);
    }

    @Description("Số lượng = \"10\", Giá nhập = \"\"")
    public void addOrderProduct_emptyProductPrice() {
        openAddOrderProductModal();
        waitForProductOptionsToLoad();
        WebUI.clearAndFillValue(productQuantityInput, "10");
        WebUI.clearInputField(productPriceInput);
        WebUI.clickButtonWithText("Thêm");
        WebUI.assertModalIsOpen(addOrderProductModalBy);
        WebUI.assertInputHasErrorText("price--input", ErrorKeys.PRODUCT_PRICE_MIN);
    }

    @Description("Số lượng = \"10\", Giá nhập = \"0\"")
    public void addOrderProduct_zeroProductPrice() {
        openAddOrderProductModal();
        waitForProductOptionsToLoad();
        WebUI.clearAndFillValue(productQuantityInput, "10");
        WebUI.clearAndFillValue(productPriceInput, "0");
        WebUI.clickButtonWithText("Thêm");
        WebUI.assertModalIsOpen(addOrderProductModalBy);
        WebUI.assertInputHasErrorText("price--input", ErrorKeys.PRODUCT_PRICE_MIN);
    }

    @Description("Số lượng = \"10\", Giá nhập = \"-1\"")
    public void addOrderProduct_negativeProductPrice() {
        openAddOrderProductModal();
        waitForProductOptionsToLoad();
        WebUI.clearAndFillValue(productQuantityInput, "10");
        WebUI.clearAndFillValue(productPriceInput, "-1");
        WebUI.clickButtonWithText("Thêm");
        WebUI.assertModalIsOpen(addOrderProductModalBy);
        WebUI.assertInputHasErrorText("price--input", ErrorKeys.PRODUCT_PRICE_MIN);
    }

    @Description("Số lượng = \"10\", Giá nhập = \"--1\"")
    public void addOrderProduct_invalidCharacterProductPrice() {
        openAddOrderProductModal();
        waitForProductOptionsToLoad();
        WebUI.clearAndFillValue(productQuantityInput, "10");
        WebUI.clearAndFillValue(productPriceInput, "--1");
        WebUI.clickButtonWithText("Thêm");
        WebUI.assertModalIsOpen(addOrderProductModalBy);
        WebUI.assertInputHasErrorText("price--input", ErrorKeys.PRODUCT_PRICE_WRONG_FORMAT);
    }

    @Description("Số lượng = \"\", Giá nhập = \"1000\"")
    public void addOrderProduct_emptyProductQuantity() {
        openAddOrderProductModal();
        waitForProductOptionsToLoad();
        WebUI.clearInputField(productQuantityInput);
        WebUI.clearAndFillValue(productPriceInput, "1000");
        WebUI.clickButtonWithText("Thêm");
        WebUI.assertModalIsOpen(addOrderProductModalBy);
        WebUI.assertInputHasErrorText("quantity--input", ErrorKeys.PRODUCT_QUANTITY_REQUIRED);
    }

    @Description("Số lượng = \"0\", Giá nhập = \"1000\"")
    public void addOrderProduct_zeroProductQuantity() {
        openAddOrderProductModal();
        waitForProductOptionsToLoad();
        WebUI.clearAndFillValue(productQuantityInput, "0");
        WebUI.clearAndFillValue(productPriceInput, "1000");
        WebUI.clickButtonWithText("Thêm");
        WebUI.assertModalIsOpen(addOrderProductModalBy);
    }

    @Description("Số lượng = \"-1\", Giá nhập = \"100000\"")
    public void addOrderProduct_negativeProductQuantity() {
        openAddOrderProductModal();
        waitForProductOptionsToLoad();
        WebUI.clearAndFillValue(productQuantityInput, "-1");
        WebUI.clearAndFillValue(productPriceInput, "100000");
        WebUI.clickButtonWithText("Thêm");
        WebUI.assertModalIsOpen(addOrderProductModalBy);
        WebUI.assertInputHasErrorText("quantity--input", ErrorKeys.PRODUCT_QUANTITY_MIN);
    }

    @Description("Số lượng = \"--1\", Giá nhập = \"100000\"")
    public void addOrderProduct_invalidCharacterProductQuantity() {
        openAddOrderProductModal();
        waitForProductOptionsToLoad();
        WebUI.clearAndFillValue(productQuantityInput, "--1");
        WebUI.clearAndFillValue(productPriceInput, "100000");
        WebUI.clickButtonWithText("Thêm");
        WebUI.assertModalIsOpen(addOrderProductModalBy);
        WebUI.assertInputHasErrorText("quantity--input", ErrorKeys.PRODUCT_QUANTITY_WRONG_FORMAT);
    }

    public void fillOrderData() {
        openCreateOrderModal();
        WebUI.clearAndFillValue(orderCodeInput, generateCode());
        WebUI.selectOptionByText(orderSupplierSelect, "NCC1");
        WebUI.clearAndFillValue(orderNoteInput, "Test note");
    }
}
