package com.MBS.Utility;


import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class UtilProperties {

    private static Properties prop = new Properties();

    public static Logger LOG = Logger.getLogger(UtilProperties.class.getName());

    public static String getProperties(String key) {

        String fileName = System.getProperty("user.dir") + "/config.properties";

        try {
            InputStream input = new FileInputStream(fileName);
            prop.load(input);
            String value = prop.getProperty(key);
            input.close();
            return value.trim(); //  Return value after removing leading and trailing blank spaces.

        } catch (IOException e) {
            e.printStackTrace();
            return "Sorry, unable to find " + fileName;
        }
    }

    public static void setProperties(String key, String value) {

        String fileName = System.getProperty("user.dir") + "/config.properties";
        try {
            InputStream inputStream = new FileInputStream(fileName);
            prop.load(inputStream);
            inputStream.close();

            FileOutputStream outputStream = new FileOutputStream(fileName);

            prop.setProperty(key, value);
            prop.store(outputStream, null);

            outputStream.close();

        } catch (IOException ioe) {
            ioe.printStackTrace();
            LOG.info("Sorry, unable to locate file in : " + fileName);
        }

    }
}
