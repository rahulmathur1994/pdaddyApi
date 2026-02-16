package com.postbackdaddy.api.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Configuration Reader Utility
 * Reads properties from config files
 */
public class ConfigReader {

    private static Properties properties;

    static {
        loadProperties();
    }

    /**
     * Load properties from config file
     */
    private static void loadProperties() {
        properties = new Properties();
        try {
            String environment = System.getProperty("env", "dev");
            String configFile = "src/test/resources/config/config-" + environment + ".properties";
            FileInputStream fis = new FileInputStream(configFile);
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            System.out.println("Loading default config-dev.properties");
            try {
                FileInputStream fis = new FileInputStream("src/test/resources/config/config.properties");
                properties.load(fis);
                fis.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Get property value by key
     * @param key Property key
     * @return Property value
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Get property value with default value
     * @param key Property key
     * @param defaultValue Default value if key not found
     * @return Property value or default value
     */
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Get base URI from configuration
     * @return Base URI
     */
    public static String getBaseURI() {
        return getProperty("base.uri");
    }

    /**
     * Get timeout from configuration
     * @return Timeout in seconds
     */
    public static int getTimeout() {
        return Integer.parseInt(getProperty("timeout", "10"));
    }

    /**
     * Get System Admin email from configuration
     * @return System Admin email
     */
    public static String getSystemAdminEmail() {
        return getProperty("system.admin.email");
    }

    /**
     * Get System Admin password from configuration
     * @return System Admin password
     */
    public static String getSystemAdminPassword() {
        return getProperty("system.admin.password");
    }
}
