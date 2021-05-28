package com.MBS.Utility;


import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

public class ScreenShot {


    public static Logger LOG = Logger.getLogger(ScreenShot.class.getName());
    private WebDriver driver = ConfigDriver.getInstance().getDriver();

    /**
     * This function will take screenshot
     * Save the image in destination fileWithPath as .png image
     *
     * @return captured screen shot name e.g. screen-8-APRIL-12:17:45.png
     * @throws IOException
     */

    public String takeScreenshot() {

        String fileWithPath = System.getProperty("user.dir") + "/src/main/java/Screenshots/screen-" + dateTime().trim() + ".png";

        String capturedImageName;
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File SrcFile = screenshot.getScreenshotAs(OutputType.FILE);
            File DestFile = new File(fileWithPath);
            System.out.println("$$$Screenshot File stored in location: "+DestFile);
            System.out.println("$$$Screenshot image stored in location: "+DestFile);
            FileUtils.copyFile(SrcFile, DestFile);
            capturedImageName = fileWithPath.substring(fileWithPath.indexOf("screen-"));

            LOG.info("only image file " + capturedImageName);

            // this.reportWithScreenshot(false,"Test step of screenshot","Failed, ",capturedImageName);
            return capturedImageName;

        } catch (IOException e) {
            return e.getMessage();
        }

    }

    private static String dateTime() {

        LocalDateTime localDateTime = LocalDateTime.now();
        String day = String.valueOf(localDateTime.getDayOfMonth());
        String month = String.valueOf(localDateTime.getMonth());
        String hour = String.valueOf(localDateTime.getHour());
        String minute = String.valueOf(localDateTime.getMinute());
        String nano = String.valueOf(localDateTime.getNano());

        return (day + "-" + month + "-" + hour + "-" + minute + "-" + nano);

    }
}
