package com.savkova.app.db.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {

    public static final class MainSettings {
        public static final String propertiesFileName = "./src/main/resources/db.properties";
        public static final DbProperties dbProperties = new DbProperties(propertiesFileName);
    }

    public static final class DbTable {
        public static final String ORDERS = "orders";
        public static final String CUSTOMERS = "customers";
        public static final String PRODUCTS = "products";
    }

    public static final class Loggers {
        public static final Logger LOGGER = LoggerFactory.getLogger("OrdersDb");
    }
}
