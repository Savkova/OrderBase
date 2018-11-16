package com.savkova.app;

import com.savkova.app.db.DbInitUtil;
import com.savkova.app.db.dao.*;
import com.savkova.app.db.entities.*;

import java.sql.SQLException;

public class Main {
    /*
        Создать проект «База данных заказов».
        Создать таблицы «Товары» , «Клиенты» и «Заказы».
        Написать код для добавления новых клиентов, товаров и оформления заказов.
    */
    public static void main(String[] args) {

        try {
            DbInitUtil.initDbConnection();
            DbInitUtil.initDb();

            ProductDao productDao = new ProductDao();
            productDao.add(new Product(12345, "Item 1", "Some info about the product"));
            productDao.add(new Product(67890, "Item 2", "Some info about the product"));

            CustomerDao customerDao = new CustomerDao();
            customerDao.add(new Customer("John", "Smith", "380971234567", "123@gmail.com"));

            OrderDao orderDao = new OrderDao();
            orderDao.add(new Order(123, 2, 1, 3, 100.0));

            Order order = orderDao.get(1);
            order.setStatus("done");
            orderDao.update(order);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbInitUtil.freeResources();
        }
    }

}
