package com.savkova.app.db.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.MessageFormat;
import java.util.Properties;

import static com.savkova.app.db.util.Constants.Loggers.LOGGER;

public class DbProperties extends Properties {
    private String url;
    private String user;
    private String password;

    public DbProperties(String fileName) {
        readPropertiesFromFile(fileName);
        initPropertiesValues();
        LOGGER.info("Configuration properties downloaded from file '" + fileName + "'");
    }

    private void readPropertiesFromFile(String fileName) {
        FileInputStream in = null;
        try {
            LOGGER.info("Reading file '" + fileName + "'...");
            in = new FileInputStream(fileName);
            this.load(in);
        } catch (IOException e) {
            LOGGER.error("File '" + fileName + "' not found or cannot be opened.", e);
            System.exit(-1);
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    private void initPropertiesValues() {
        url = this.getProperty(PropertyKeys.db_url.toString());
        user = this.getProperty(PropertyKeys.db_user.toString());
        password = this.getProperty(PropertyKeys.db_password.toString());
    }

    @Override
    public String getProperty(String key) {
        final String property = super.getProperty(key);
        if (property == null) {
            throw new InvalidParameterException(MessageFormat.format("Missing value for key {0}", key));
        }
        return property;
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        String property = super.getProperty(key, defaultValue);
        if (property.isEmpty()) {
            throw new InvalidParameterException(MessageFormat.format("Missing value for key {0}", key));
        }
        return property;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public enum PropertyKeys {
        db_url,
        db_user,
        db_password
    }
}
