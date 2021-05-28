package com.MBS.Utility;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.*;
import java.time.LocalDateTime;

/*import org.apache.logging.log4j.Logger;*/

public class LoggerUtility {

    private static String logPath = "src/Log/" + directoryName() + "/log.log";

    private PrintWriter printWriter = null;

    public LoggerUtility() {

        File file = new File(logPath);
        file.getParentFile().mkdirs();

        /*Instance member*/
        FileWriter writer = null;
        try {
            writer = new FileWriter(file, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        printWriter = new PrintWriter(bufferedWriter);

    }


    public void DEBUG(String logMessage) {

        LocalDateTime date = LocalDateTime.now();

        System.setProperty("fileName", logPath);
        Logger log = Logger.getLogger(LoggerUtility.class);
        log.debug(logMessage);

        String dateTime = String.valueOf(date.getDayOfMonth()) + "-"
                + String.valueOf(date.getMonthValue()) + "-"
                + String.valueOf(date.getYear()) + "-"
                + String.valueOf(date.getHour()) + ":"
                + String.valueOf(date.getMinute()) + ":"
                + String.valueOf(date.getSecond());

        printWriter.print(dateTime + " || " + " [ DEBUG] " + logMessage + "\n");
        printWriter.flush();
    }

    public void FATAL(String logMessage) {
        printWriter.print("\n" + "Log Level Fatal:::: " + logMessage);
        printWriter.flush();
    }

    public void INFO(String logMessage) {
        printWriter.print("\n" + "Log Level INFO:::: " + logMessage);
        printWriter.flush();
    }

    public void ERROR(String logMessage) {
        printWriter.print("\n" + "Log Level ERROR:::: " + logMessage);
        printWriter.flush();
    }


    private static String directoryName() {

        LocalDateTime localDateTime = LocalDateTime.now();
        String day = String.valueOf(localDateTime.getDayOfMonth());
        String month = String.valueOf(localDateTime.getMonthValue());
        String year = String.valueOf(localDateTime.getYear());
        String hour = String.valueOf(localDateTime.getHour());
        String minute = String.valueOf(localDateTime.getMinute());
        String seconds = String.valueOf(localDateTime.getSecond());

        return (day + "-" + month + "-" + year + " " + hour + ":" + minute + ":" + seconds);

    }

}



