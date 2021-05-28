package com.MBS.Utility;

import org.testng.log4testng.Logger;

public class ApplicationLogger {

    Logger logger = Logger.getLogger(this.getClass());

    public void storeLogs(String logMessage) {


        logger.info(logMessage);
        System.out.println("TESTTSTSTSTT");

    }


}
