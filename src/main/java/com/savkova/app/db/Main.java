package com.savkova.app.db;

import com.savkova.app.db.entities.*;

import java.sql.*;

import static com.savkova.app.db.util.Constants.MainSettings;
import static com.savkova.app.db.util.Constants.DbTable;
import static com.savkova.app.db.util.Constants.Loggers.LOGGER;

public class Main {

    private static Connection conn;
    private static Statement statement;
    private static PreparedStatement preparedStatement;

    /*
        Создать проект «База данных заказов».
        Создать таблицы «Товары» , «Клиенты» и «Заказы».
        Написать код для добавления новых клиентов, товаров и оформления заказов.
    */
    public static void main(String[] args) {

        try {
            initDbConnection();
            initDb();

            Product product1 = new Product(12345, "Item 1", "Some info about the product");
            addProduct(product1.getVendorCode(), product1.getName(), product1.getDescription());
            LOGGER.info("Added " + product1);

            Product product2 = new Product(67890, "Item 2", "Some info about the product");
            addProduct(product2.getVendorCode(), product2.getName(), product2.getDescription());
            LOGGER.info("Added " + product2);

            Customer customer1 = new Customer("John", "Smith", "380971234567", "123@gmail.com");
            addCustomer(customer1.getFirstName(), customer1.getLastName(), customer1.getPhone(), customer1.getEmail());
            LOGGER.info("Added " + customer1);

            Order order = new Order(123, 2, 1, 3, 100.0);
            addOrder(order.getContractNumber(),
                    order.getOrderDate(),
                    order.getCustomerId(),
                    order.getProductId(),
                    order.getQuantity(),
                    order.getPrice(),
                    order.getStatus());
            LOGGER.info("Added " + order);

        } catch (ClassNotFoundException e) {
            LOGGER.error("Driver not found in classpath.", e);
            System.exit(-1);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    LOGGER.info("Connection closed");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void addProduct(int id, String name, String descripthion) throws SQLException {

        preparedStatement = conn.prepareStatement("INSERT INTO " + DbTable.PRODUCTS
                + "(product_id, product_name, description) values (?,?,?)");
        try {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, descripthion);
            preparedStatement.executeUpdate();
        } finally {
            preparedStatement.close();
        }
    }

    private static void addCustomer(String firstName, String lastName, String phone, String email) throws SQLException {

        preparedStatement = conn.prepareStatement("INSERT into " + DbTable.CUSTOMERS
                + "(first_name, last_name, phone, email) values (?,?,?,?)");
        try {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, phone);
            preparedStatement.setString(4, email);
            preparedStatement.executeUpdate();
        } finally {
            preparedStatement.close();
        }
    }

    private static void addOrder(int contructNumber, Date date, int customerId, int productId, int quantity, double price, String status) throws SQLException {

        preparedStatement = conn.prepareStatement("INSERT INTO " + DbTable.ORDERS
                + "(customer_id, product_id, order_date, contract_number, quantity, price, status)"
                + "VALUES (?,?,?,?,?,?,?)");
        try {
            preparedStatement.setInt(1, customerId);
            preparedStatement.setInt(2, productId);
            preparedStatement.setDate(3, date);
            preparedStatement.setInt(4, contructNumber);
            preparedStatement.setInt(5, quantity);
            preparedStatement.setDouble(6, price);
            preparedStatement.setString(7, status);
            preparedStatement.executeUpdate();
            preparedStatement.executeUpdate();
        } finally {
            preparedStatement.close();
        }
    }

    private static void initDbConnection() throws ClassNotFoundException, SQLException {

        LOGGER.info("Register JDBC driver...");
        Class.forName(MainSettings.JDBC_DRIVER);

        String url = MainSettings.dbProperties.getUrl();
        String user = MainSettings.dbProperties.getUser();
        String password = MainSettings.dbProperties.getPassword();

        LOGGER.info("Connecting to database...");
        conn = DriverManager.getConnection(url, user, password);
        LOGGER.info("Connection to the database is established successfully");
    }

    private static void initDb() throws SQLException {

        createTableProducts();
        createTableCustomers();
        createTableOrders();
    }

    private static void createTableProducts() throws SQLException {

        String query = "CREATE TABLE IF NOT EXISTS " + DbTable.PRODUCTS + " (" +
                "id INT(64) NOT NULL AUTO_INCREMENT, " +
                "product_id INT(64) NOT NULL, " +
                "product_name VARCHAR(45) NOT NULL, " +
                "description VARCHAR(200) NULL, " +
                "PRIMARY KEY(id))";

        try {
            statement = conn.createStatement();
            statement.executeUpdate(query);
            LOGGER.info("Table '" + DbTable.PRODUCTS + "' created (if not already exist)");
        } catch (SQLException e) {
            LOGGER.error("In method createTableProducts(): ", e);
        } finally {
            statement.close();
        }

    }

    private static void createTableCustomers() throws SQLException {

        String query = "CREATE TABLE IF NOT EXISTS " + DbTable.CUSTOMERS + " (" +
                "id INT(64) NOT NULL AUTO_INCREMENT, " +
                "first_name VARCHAR(45) NOT NULL, " +
                "last_name VARCHAR(45) NOT NULL, " +
                "phone VARCHAR(12) NOT NULL, " +
                "email VARCHAR(45) NOT NULL, " +
                "PRIMARY KEY(id))";

        try {
            statement = conn.createStatement();
            statement.executeUpdate(query);
            LOGGER.info("Table '" + DbTable.CUSTOMERS + "' created (if not already exist)");
        } catch (SQLException e) {
            LOGGER.error("In method createTableCustomers(): ", e);
        } finally {
            statement.close();
        }
    }

    private static void createTableOrders() throws SQLException {

        String query = "CREATE TABLE IF NOT EXISTS " + DbTable.ORDERS + " (" +
                "id INT(64) NOT NULL AUTO_INCREMENT, " +
                "customer_id INT(64) NOT NULL, " +
                "product_id INT(64) NOT NULL, " +
                "order_date TIMESTAMP NOT NULL, " +
                "contract_number INT(64) NOT NULL, " +
                "quantity INT(64) NOT NULL, " +
                "price DOUBLE NOT NULL, " +
                "status VARCHAR(45) NOT NULL, " +
                "PRIMARY KEY(id), " +
                "CONSTRAINT fk_customer_order FOREIGN KEY (customer_id) " +
                "REFERENCES " + DbTable.CUSTOMERS + "(id), " +
                "CONSTRAINT fk_products_order FOREIGN KEY (product_id) " +
                "REFERENCES " + DbTable.PRODUCTS + "(id))";

        try {
            statement = conn.createStatement();
            statement.executeUpdate(query);
            LOGGER.info("Table '" + DbTable.ORDERS + "' created (if not already exist)");
        } catch (SQLException e) {
            LOGGER.error("In method createTableOrders(): ", e);
        } finally {
            statement.close();
        }
    }

}
