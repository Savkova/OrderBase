package com.savkova.app.db;

import com.savkova.app.db.util.Constants;

import java.sql.*;

import static com.savkova.app.db.util.Constants.Loggers.LOGGER;

public class DbInitUtil {
    private static Connection conn;
    private static Statement statement;

    public static void initDbConnection() throws SQLException {

        String url = Constants.MainSettings.dbProperties.getUrl();
        String user = Constants.MainSettings.dbProperties.getUser();
        String password = Constants.MainSettings.dbProperties.getPassword();

        LOGGER.info("Connecting to database...");
        conn = DriverManager.getConnection(url, user, password);
        LOGGER.info("Connection to the database is established successfully");
    }

    public static void initDb() throws SQLException {

        createTableProducts();
        createTableCustomers();
        createTableOrders();
    }

    private static void createTableProducts() throws SQLException {

        String query = "CREATE TABLE IF NOT EXISTS " + Constants.DbTable.PRODUCTS + " (" +
                "id INT(64) NOT NULL AUTO_INCREMENT, " +
                "product_id INT(64) NOT NULL, " +
                "product_name VARCHAR(45) NOT NULL, " +
                "description VARCHAR(200) NULL, " +
                "PRIMARY KEY(id))";

        try {
            statement = conn.createStatement();
            statement.executeUpdate(query);
            LOGGER.info("Table '" + Constants.DbTable.PRODUCTS + "' created (if not already exist)");
        } catch (SQLException e) {
            LOGGER.error("In method createTableProducts(): ", e);
        } finally {
            statement.close();
        }

    }

    private static void createTableCustomers() throws SQLException {

        String query = "CREATE TABLE IF NOT EXISTS " + Constants.DbTable.CUSTOMERS + " (" +
                "id INT(64) NOT NULL AUTO_INCREMENT, " +
                "first_name VARCHAR(45) NOT NULL, " +
                "last_name VARCHAR(45) NOT NULL, " +
                "phone VARCHAR(12) NOT NULL, " +
                "email VARCHAR(45) NOT NULL, " +
                "PRIMARY KEY(id))";

        try {
            statement = conn.createStatement();
            statement.executeUpdate(query);
            LOGGER.info("Table '" + Constants.DbTable.CUSTOMERS + "' created (if not already exist)");
        } catch (SQLException e) {
            LOGGER.error("In method createTableCustomers(): ", e);
        } finally {
            statement.close();
        }
    }

    private static void createTableOrders() throws SQLException {

        String query = "CREATE TABLE IF NOT EXISTS " + Constants.DbTable.ORDERS + " (" +
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
                "REFERENCES " + Constants.DbTable.CUSTOMERS + "(id), " +
                "CONSTRAINT fk_products_order FOREIGN KEY (product_id) " +
                "REFERENCES " + Constants.DbTable.PRODUCTS + "(id))";

        try {
            statement = conn.createStatement();
            statement.executeUpdate(query);
            LOGGER.info("Table '" + Constants.DbTable.ORDERS + "' created (if not already exist)");
        } catch (SQLException e) {
            LOGGER.error("In method createTableOrders(): ", e);
        } finally {
            statement.close();
        }
    }

    public static Connection getConn() {
        return conn;
    }

    public static void freeResources() {
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
