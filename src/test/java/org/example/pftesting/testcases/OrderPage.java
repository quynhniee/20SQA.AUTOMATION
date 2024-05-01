package org.example.pftesting.testcases;

import org.example.pftesting.pages.CommonPage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.open;

public class OrderPage extends CommonPage {


    @BeforeClass
    public void setup() {
        open("https://quanlycuahangcom.netlify.app/orders");
    }

    @Test(groups = "orderPage", description = "Đảm bảo trang quản lý đơn hàng được load thành công")
    public void pageIsLoaded() {
        getOrdersManagement().pageIsLoaded();
    }

    @Test(groups = "orderPage", description = "Kiểm tra chức năng bỏ chọn tất cả", dependsOnMethods = "pageIsLoaded")
    public void unSelectAll() {
        getOrdersManagement().unSelectButtonPerformsCorrectly();
    }

    @Test(groups = "orderPage", description = "Kiểm tra bulk action buttons xuất hiện", dependsOnMethods = "pageIsLoaded")
    public void bulkActionButtonsAppear() {
        getOrdersManagement().bulkActionsPerformCorrectly();
    }
}
