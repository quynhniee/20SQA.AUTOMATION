package org.example.pftesting.pages;

import org.example.pftesting.base.BaseTest;
import org.example.pftesting.pages.orders.OrdersManagement;
import org.example.pftesting.pages.orders.OrdersPage;

public class CommonPage extends BaseTest {
    private OrdersPage importOrder;
    private OrdersManagement ordersManagement;

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
}
