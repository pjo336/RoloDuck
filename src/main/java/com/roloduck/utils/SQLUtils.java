package com.roloduck.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Andrew Ertell
 * @author Peter Johnston
 * @since 3/25/14
 * RoloDuck
 */

public class SQLUtils {

    public static void printSQL(String SQL) {
        SQLUtils util = new SQLUtils();
        if(util.showSQL().equalsIgnoreCase("true")) {
            System.out.println(SQL);
        }
    }

    private String showSQL() {
        String propName = "config/properties/application.properties";
        Properties properties = new Properties();
        try {
            InputStream in = getInputStreamForPropertiesFile(propName);
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException npe) {
            System.out.println("This property file doesnt exist: " + propName);
        }
        return properties.getProperty("show_sql");
    }

    private InputStream getInputStreamForPropertiesFile(String propertyFileName) {
        return getClass().getClassLoader().getResourceAsStream(propertyFileName);
    }
}
