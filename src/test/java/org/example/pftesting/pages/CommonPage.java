package org.example.pftesting.pages;

import org.example.pftesting.base.BaseTest;
import org.example.pftesting.pages.orders.OrdersManagement;
import org.example.pftesting.pages.orders.OrdersPage;
import org.example.pftesting.pages.suppliers.SupplierManagement;

public class CommonPage extends BaseTest {
    private OrdersPage importOrder;
    private OrdersManagement ordersManagement;
    private SupplierManagement supplierManagement;

    public OrdersPage getImportOrderPage() {
        if (importOrder == null) {
            importOrder = new OrdersPage();
        }
        return importOrder;
    }

    public OrdersManagement getOrdersManagement() {
        if (ordersManagement == null) {
            ordersManagement = new OrdersManagement();
        }
        return ordersManagement;
    }

    public SupplierManagement getSupplierManagement() {
        if (supplierManagement == null) {
            supplierManagement = new SupplierManagement();
        }
        return supplierManagement;
    }
}
