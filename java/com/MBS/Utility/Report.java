//package com.MBS.Utility;
//
//import com.MBS.Init.APIResponse;
//import com.MBS.Init.HTTPStatusCode;
//import com.relevantcodes.extentreports.ExtentReports;
//import com.relevantcodes.extentreports.ExtentTest;
//import com.relevantcodes.extentreports.LogStatus;
//import org.jbehave.core.annotations.Given;
//import org.jbehave.core.annotations.Then;
//import org.jbehave.core.annotations.When;
//import org.testng.annotations.AfterClass;
//
//import java.lang.reflect.Method;
//
//
//public class Report {
//
//    private ExtentReports extent;
//    private ExtentTest logger;
//    private String storyName;
//
//    private ResultProperties rp = new ResultProperties();
//
//    int passCount;
//    int failCount;
//
//    public Report(ExtentReports reports, String testCaseName, String storyNm) {
//        this.extent = reports;
//        this.logger = extent.startTest(testCaseName);
//        this.storyName = storyNm;
//    }
//    /* Below location should be is used in variable screenshotReportingPath w.r.t its execution environment:
//     * For CI-Server:  "https://ci.peachpayments.com/job/hpp_checkout_test/ws/src/main/java/Screenshots/"
//     * For Local Machine: "http://localhost:63342/peach-automation/Screenshots/"
//     * */
//    private  String screenshotReportingPath ="https://ci.peachpayments.com/job/hpp_api_automation/ws/src/main/java/Screenshots/";
//
//    /* Below location should be is used when executed from local machine */
////    private  String screenshotReportingPath="http://localhost:63342/peach-automation/Screenshots/";
//
//    public void sendInfo(Object obClass) {
//        Method m = getCurrentMethod(obClass);
//        info(getStepMessage(m));
//    }
//
//    public void customInfo(String info) {
//        info(info);
//    }
//
//    public void customInfowithOb(Object obClass, String info) {
//        Method m = getCurrentMethod(obClass);
//        logger.log(LogStatus.INFO,getStepMessage(m),info);
//        extent.flush();
//    }
//
//    private String getStepMessage(Method m) {
//
//        if (m.isAnnotationPresent(Given.class)) {
//            Given ta = m.getAnnotation(Given.class);
//            return "Given " + ta.value();
//        } else if (m.isAnnotationPresent(When.class)) {
//            When ta = m.getAnnotation(When.class);
//            return "When " + ta.value();
//        } else if (m.isAnnotationPresent(Then.class)) {
//            Then ta = m.getAnnotation(Then.class);
//            return "Then " + ta.value();
//        }
//        return null;
//    }
//
//    private Method getCurrentMethod(Object o) {
//        String s = Thread.currentThread().getStackTrace()[3].getMethodName();
//        Method cm = null;
//        for (Method m : o.getClass().getMethods()) {
//            if (m.getName().equals(s)) {
//                cm = m;
//                break;
//            }
//        }
//        return cm;
//    }
//
//    public void pass(Object obClass, String successMessage) {
//        Method m = getCurrentMethod(obClass);
//        logger.log(LogStatus.PASS,getStepMessage(m),successMessage);
//        passCount = passCount +1;
//        rp.setResultProperties(storyName+"_PASS",String.valueOf(passCount));
//        extent.flush();
//    }
//
//    public void pass(String stepName, String successMessage) {
//        logger.log(LogStatus.PASS, stepName, successMessage);
//        passCount = passCount +1;
//        rp.setResultProperties(storyName+"_PASS",String.valueOf(passCount));
//        extent.flush();
//    }
//
//    public void pass(Object obClass, String successMessage, String screenShot) {
//        String finalScreenshot = screenshotReportingPath + screenShot;
//        Method m = getCurrentMethod(obClass);
//        logger.log(LogStatus.PASS, getStepMessage(m), successMessage + " Screenshot: " + logger.addScreenCapture(finalScreenshot));
//        passCount = passCount +1;
//        rp.setResultProperties(storyName+"_PASS",String.valueOf(passCount));
//        extent.flush();
//    }
//
//    public void pass(String stepName, String successMessage, String screenShot) {
//        String finalScreenshot = screenshotReportingPath + screenShot;
//        logger.log(LogStatus.PASS, stepName, successMessage + " Screenshot: " + logger.addScreenCapture(finalScreenshot));
//        passCount = passCount +1;
//        rp.setResultProperties(storyName+"_PASS",String.valueOf(passCount));
//        extent.flush();
//    }
//
//    public void fail(Object obClass, String errorMessage) {
//        Method m = getCurrentMethod(obClass);
//        logger.log(LogStatus.FAIL, getStepMessage(m), errorMessage);
//        failCount = failCount +1;
//        rp.setResultProperties(storyName+"_FAIL",String.valueOf(failCount));
//        rp.setResultProperties(storyName,"FAIL");
//        extent.flush();
//    }
//
//    public void fail(String stepName, String errorMessage) {
//        logger.log(LogStatus.FAIL, stepName, errorMessage);
//        failCount = failCount +1;
//        rp.setResultProperties(storyName+"_FAIL",String.valueOf(failCount));
//        rp.setResultProperties(storyName,"FAIL");
//        extent.flush();
//    }
//
//    public void fail(Object obClass, String errorMessage, String screenShot) {
//
//        String finalScreenshot = screenshotReportingPath + screenShot;
//        Method m = getCurrentMethod(obClass);
//        logger.log(LogStatus.FAIL, getStepMessage(m), errorMessage + " Screenshot: " + logger.addScreenCapture(finalScreenshot));
//        failCount = failCount +1;
//        rp.setResultProperties(storyName+"_FAIL",String.valueOf(failCount));
//        rp.setResultProperties(storyName,"FAIL");
//        extent.flush();
//    }
//
//    public void fail(String stepName, String errorMessage, String screenShot) {
//        String finalScreenshot = screenshotReportingPath + screenShot;
//        logger.log(LogStatus.FAIL, stepName, errorMessage + " Screenshot: " + logger.addScreenCapture(finalScreenshot));
//        failCount = failCount +1;
//        rp.setResultProperties(storyName+"_FAIL",String.valueOf(failCount));
//        rp.setResultProperties(storyName,"FAIL");
//        extent.flush();
//    }
//
//    public void info ( String information ) {
//
//        logger.log (LogStatus.INFO, information);
//        extent.flush();
//    }
//
//
//    public  void validateStatusCodeAndReport(int statusCode, String parameterName, boolean isNegativeScenario){
//
//        if (isNegativeScenario){
//
//            if (statusCode == HTTPStatusCode.HTTP_OK ||
//                    statusCode== HTTPStatusCode.HTTP_Created||
//                    statusCode==HTTPStatusCode.HTTP_Accepted){
//
//             fail(false, "API should have return HTTP status code 400, when invalid/ Incorrect parameter : "+parameterName+"\n"
//                     + APIResponse.getInstance().getResponse().prettyPrint());
//
//            }else if (statusCode== HTTPStatusCode.HTTP_Bad_Request){
//
//                pass(true,"-Ve test case pass, API returned 400 for invalid parameter :"+parameterName);
//
//            }else if (statusCode== HTTPStatusCode.HTTP_Internal_Server_Error||
//                    statusCode== HTTPStatusCode.HTTP_Bad_Gateway){
//
//                fail(false, "Test case failed, Bad gateway error occur.  Please recheck the API request JSON");
//
//            }else if (statusCode== HTTPStatusCode.HTTP_Forbidden ||
//                    statusCode== HTTPStatusCode.HTTP_Unauthorized){
//
//                pass(true, "-Ve test case passed, permission denied response throws");
//
//            }else
//                fail(false, "Something unexpected happen, HTTP status code: "+statusCode+" returned");
//
//        }else {
//
//            if (statusCode == HTTPStatusCode.HTTP_OK ||
//                    statusCode== HTTPStatusCode.HTTP_Created||
//                    statusCode==HTTPStatusCode.HTTP_Accepted){
//
//                pass(true, "API Call successful, it return status code :"+statusCode);
//
//            }else if (statusCode== HTTPStatusCode.HTTP_Bad_Request){
//
//                fail(false,"Test case failed, API returned 400 as status code:"+parameterName);
//
//            }else if (statusCode== HTTPStatusCode.HTTP_Internal_Server_Error||
//                    statusCode== HTTPStatusCode.HTTP_Bad_Gateway||
//                    statusCode== HTTPStatusCode.HTTP_Gateway_Timeout){
//
//                fail(false, "Test case failed, Bad gateway error occur.  Please recheck the API request JSON");
//
//            }else if (statusCode== HTTPStatusCode.HTTP_Forbidden ||
//                    statusCode== HTTPStatusCode.HTTP_Unauthorized){
//
//                fail(false, "Test case failed, permission denied response throws");
//
//            }else
//                fail(false, "Something unexpected happen, HTTP status code: "+statusCode+" returned");
//        }
//    }
//
//    public void assertDescriptionAndReport(int statusCode, boolean isTrue, String logFailedMessage){
//
//        if (statusCode >400){
//            if (isTrue){
//                pass(true,"Validation successful, error description matched with expected description");
//            }else
//                fail(false, logFailedMessage);
//        }else
//            fail(false, "Something wrong just happened, the API should have return 400 status code");
//    }
//
//    @AfterClass
//    public void endReport() {
//        extent.flush();
//        extent.endTest(logger);
//    }
//
//}
