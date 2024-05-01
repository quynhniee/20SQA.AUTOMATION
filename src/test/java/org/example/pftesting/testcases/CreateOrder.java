package org.example.pftesting.testcases;

import org.example.pftesting.pages.CommonPage;
import org.testng.annotations.*;

import static com.codeborne.selenide.Selenide.open;

public class CreateOrder extends CommonPage {

    @BeforeSuite
    public void setup() {
        open("https://quanlycuahangcom.netlify.app/orders");
    }

    @BeforeClass
    public void openModal() {
        getImportOrderPage().openCreateOrderModal();
    }

    @AfterTest
    public void closeModal() {
        getImportOrderPage().closeCreateOrderModal();
    }


    @Test (groups = "createOrder", priority = -1, description = "Tạo đơn hàng bỏ trống toàn bộ data")
    void createOrder_invalidData_emptyCode() {
        getImportOrderPage().createOrder_blankData();
    }

    @Test (groups = "createOrder", priority = 1, description = "Tạo đơn hàng với Mã đơn hàng = \"123\"")
    void createOrder_invalidData_invalidCode() {
        getImportOrderPage().createOrder_invalidCode_wrongFormat();
    }

    @Test (groups = "createOrder", priority = 1, description = "Tạo đơn hàng với Mã đơn hàng = \"  123\"")
    void createOrder_invalidData_containsSpace() {
        getImportOrderPage().createOrder_invalidCode_containsSpace();
    }

    @Test (groups = "createOrder", priority = 1, description = "Tạo đơn hàng với Mã đơn hàng = \"  DH123\"")
    void createOrder_invalidData_startWithSpace() {
        getImportOrderPage().createOrder_invalidCode_startWithSpace();
    }

    @Test (groups = "createOrder", priority = 1, description = "Tạo đơn hàng với Mã đơn hàng = \"¥¥123\"")
    void createOrder_invalidData_specialCharacterCode() {
        getImportOrderPage().createOrder_invalidCode_specialCharacterCode();
    }

    @Test (groups = "createOrder", priority = 2, description = "Thêm sản phẩm vào đơn hàng với data mặc định")
    void createOrder_invalidData_defaultProductData() {
        getImportOrderPage().openAddOrderProductModal();
        getImportOrderPage().addOrderProduct_defaultData();
        getImportOrderPage().closeAddOrderProductModal();
    }

    @Test (groups = "createOrder_addProduct", priority = 2, description = "Thêm sản phẩm vào đơn hàng với data trống")
    void createOrder_invalidData_emptyProductData() {
        getImportOrderPage().openAddOrderProductModal();
        getImportOrderPage().addOrderProduct_emptyData();
        getImportOrderPage().closeAddOrderProductModal();
    }

    @Test (groups = "createOrder_addProduct", priority = 2, description = "Thêm sản phẩm vào đơn hàng với số lượng trống")
    void createOrder_invalidData_emptyProductQuantity() {
        getImportOrderPage().openAddOrderProductModal();
        getImportOrderPage().addOrderProduct_emptyProductQuantity();
        getImportOrderPage().closeAddOrderProductModal();
    }

    @Test (groups = "createOrder_addProduct", priority = 2, description = "Thêm sản phẩm vào đơn hàng với số lượng = 0")
    void createOrder_invalidData_zeroProductQuantity() {
        getImportOrderPage().openAddOrderProductModal();
        getImportOrderPage().addOrderProduct_zeroProductQuantity();
        getImportOrderPage().closeAddOrderProductModal();
    }

    @Test (groups = "createOrder_addProduct", priority = 2, description = "Thêm sản phẩm vào đơn hàng với số lượng = -1")
    void createOrder_invalidData_negativeProductQuantity() {
        getImportOrderPage().openAddOrderProductModal();
        getImportOrderPage().addOrderProduct_negativeProductQuantity();
        getImportOrderPage().closeAddOrderProductModal();
    }

    @Test (groups = "createOrder_addProduct", priority = 2, description = "Thêm sản phẩm vào đơn hàng với số lượng = --1")
    void createOrder_invalidData_negativeNegativeProductQuantity() {
        getImportOrderPage().openAddOrderProductModal();
        getImportOrderPage().addOrderProduct_invalidCharacterProductQuantity();
        getImportOrderPage().closeAddOrderProductModal();
    }

    @Test (groups = "createOrder_addProduct", priority = 2, description = "Thêm sản phẩm vào đơn hàng với giá trống")
    void createOrder_invalidData_emptyProductPrice() {
        getImportOrderPage().openAddOrderProductModal();
        getImportOrderPage().addOrderProduct_emptyProductPrice();
        getImportOrderPage().closeAddOrderProductModal();
    }

    @Test (groups = "createOrder_addProduct", priority = 2, description = "Thêm sản phẩm vào đơn hàng với giá = 0")
    void createOrder_invalidData_zeroProductPrice() {
        getImportOrderPage().openAddOrderProductModal();
        getImportOrderPage().addOrderProduct_zeroProductPrice();
        getImportOrderPage().closeAddOrderProductModal();
    }

    @Test (groups = "createOrder_addProduct", priority = 2, description = "Thêm sản phẩm vào đơn hàng với giá = -1")
    void createOrder_invalidData_negativeProductPrice() {
        getImportOrderPage().openAddOrderProductModal();
        getImportOrderPage().addOrderProduct_negativeProductPrice();
        getImportOrderPage().closeAddOrderProductModal();
    }

    @Test (groups = "createOrder_addProduct", priority = 2, description = "Thêm sản phẩm vào đơn hàng với giá = --1")
    void createOrder_invalidData_invalidCharacterProductPrice() {
        getImportOrderPage().openAddOrderProductModal();
        getImportOrderPage().addOrderProduct_invalidCharacterProductPrice();
        getImportOrderPage().closeAddOrderProductModal();
    }
}
