package com.MBS.Utility;


import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import static com.MBS.Utility.UtilProperties.getProperties;

public class ConfigDriver {
    private static String OS_NAME = System.getProperty("os.name");
    public static Logger LOG = Logger.getLogger(ConfigDriver.class.getName());

    private ConfigDriver() {
    }

    private static ConfigDriver instance = new ConfigDriver();

    public static ConfigDriver getInstance() {
        return instance;
    }

    public WebDriver getDriver() {
        return driverThLocal.get();
    }

    ThreadLocal<WebDriver> driverThLocal = new ThreadLocal<WebDriver>() {

        @Override
        protected WebDriver initialValue() {

            String browserName = getProperties("BrowserName");
            String driverPath = getDriverPath();
            RemoteWebDriver localDriver = null;

            if ("Chrome".equals(browserName)) {
                LOG.info("Launching Chrome browser");
                /*if (StringUtils.containsIgnoreCase(System.getProperty("os.name"), "windows")) {
                    System.setProperty("webdriver.chrome.driver", driverPath + "/chromedriver.exe");
                } else {
                    System.setProperty("webdriver.chrome.driver", driverPath + "/chromedriver");
                }
                 localDriver = new ChromeDriver();*/
                DesiredCapabilities capabilities = DesiredCapabilities.chrome();
                capabilities.setBrowserName("chrome");
                String CIServerURL="http://54.154.156.82:4444/wd/hub";
                String localServerURL="http://localhost:4444/wd/hub";

                try {
                    localDriver = new RemoteWebDriver(new URL(CIServerURL), capabilities);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }


                maximizeWindow(localDriver);
                localDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
                //localDriver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);

            } else if ("Firefox".equals(browserName)) {
                LOG.info("Launching Firefox browser");
                if (StringUtils.containsIgnoreCase(System.getProperty("os.name"), "windows")) {
                    System.setProperty("webdriver.gecko.driver", driverPath + "/geckodriver.exe");
                } else {
                    System.setProperty("webdriver.gecko.driver", driverPath + "/geckodriver");
                }

                localDriver = new FirefoxDriver();
                localDriver.manage().window().maximize();
                localDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                localDriver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

            } else {
                LOG.info("Browser name in properties file is incorrect please re-check!");
            }
            return localDriver;
        }
    };

    private String getDriverPath() {

        String driverPath = null;
        String OS = System.getProperty("os.name");

        LOG.info("your current OS is " + OS);

        if (OS.equals("Linux")) {
            driverPath = System.getProperty("user.dir") + "/drivers/linux";

        } else if (StringUtils.containsIgnoreCase(OS, "windows")) {
            driverPath = System.getProperty("user.dir") + "/drivers/windows";

        } else if (OS.equalsIgnoreCase("Mac") || OS.equalsIgnoreCase("mac os x")) {
            driverPath = System.getProperty("user.dir") + "/drivers/mac";
        }

        return driverPath;
    }

    public static void maximizeWindow(WebDriver driver) {

        if (OS_NAME.equalsIgnoreCase("Mac OS X")) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Integer width = (int) screenSize.getWidth();
            Integer height = (int) screenSize.getHeight();

            org.openqa.selenium.Point targetPosition = new org.openqa.selenium.Point(0, 0);
            driver.manage().window().setPosition(targetPosition);
            org.openqa.selenium.Dimension targetSize = new org.openqa.selenium.Dimension(width, height);
            driver.manage().window().setSize(targetSize);

        } else {
            driver.manage().window().maximize();
        }
    }

}