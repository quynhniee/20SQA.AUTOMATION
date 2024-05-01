package org.example.pftesting.testcases.suppliers;

import org.example.pftesting.pages.CommonPage;
import org.example.pftesting.pages.suppliers.SupplierManagement;
import org.testng.annotations.*;

import static com.codeborne.selenide.Selenide.*;

public class SupplierPage extends CommonPage {
    @BeforeClass
    public void setup() {
        open("https://quanlycuahangcom.netlify.app/suppliers");
    }

    @Test(groups = "supplierPage", description = "Đảm bảo trang quản lý nhà cung cấp được load thành công")
    public void pageIsLoaded() {
        getSupplierManagement().pageIsLoaded();
    }

    @Test(groups = "supplierPage", description = "Kiểm tra chức năng button thêm nhà cung cấp", dependsOnMethods = "pageIsLoaded")
    public void addSupplier() {
        getSupplierManagement().addSupplierButtonPerformsCorrectly();
    }

    @Test(groups = "supplierPage", description = "Kiểm tra chức năng chọn tất cả", dependsOnMethods = "pageIsLoaded")
    public void selectAll() {
        getSupplierManagement().tableHeaderCheckboxPerformsSelectAllCorrectly();
    }

    @Test(groups = "supplierPage", description = "Kiểm tra chức năng bỏ chọn tất cả", dependsOnMethods = "pageIsLoaded")
    public void unSelectAll() {
        getSupplierManagement().tableHeaderCheckboxPerformsUnselectAllCorrectly();
    }
}
