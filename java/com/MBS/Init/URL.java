package com.MBS.Init;

import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import com.MBS.Utility.TestData;

public class URL {

	private static Logger log = LogManager.getLogger(URL.class.getName());
	public static String testUrl = "";
	protected String targetBrowser;
	public static String URIIs ="";
    protected CommonMethodsVerification commonMethodsVerification;
    protected CommonMethodsIndexPage commonMethodsIndexPage;
    protected static WebDriver driver;
    
	public static String getEndPoint() {
		log.info("Base URL : "+testUrl);
		return testUrl;
	}

	public static String getEndPoint(String resources) {
		log.info("URL EndPoint : "+testUrl + resources);
		return testUrl + resources;
	}
	
	public void logStep(int msg1, String msg2) 
	{
		//Reporter.log("Step-"+msg1+" : "+msg2 + "</br>");
		Reporter.log(msg2 + "</br>");
		System.out.println("Step-"+msg1+" : "+msg2);// for jenkins  
	}

	public void fetchSuite(ITestContext testContext) throws IOException {

		String execution = testContext.getCurrentXmlTest().getParameter("ExecutionByXML");
		try {
		} catch (Exception e) {
		}

		if (execution.equalsIgnoreCase("true")) {
			testUrl = testContext.getCurrentXmlTest().getParameter("URL");
			targetBrowser = testContext.getCurrentXmlTest().getParameter("Browser");
		} else {
			testUrl = TestData.getValueFromConfig("config.properties", "PortalURL");
			//targetBrowser = TestData.getValueFromConfig("config.properties", "Browser");
		}
		
		URL.getEndPoint(URIIs);
	}
	
	@BeforeMethod(alwaysRun = true)
	public void setUp(Method method, ITestContext testContext,ITestResult testResult) throws IOException, InterruptedException 
	{
		
		driver.get(testUrl);
		
		suiteName = testContext.getSuite().getName();
		
		// Login
		loginIndexPage = new LoginIndexPage(driver);
		loginVerification = new LoginVerification(driver);
		
		
		commonMethodsVerification = new CommonMethodsVerification(driver);
	    commonMethodsIndexPage = new CommonMethodsIndexPage(driver);
	     
		 
		 matchdataIndexPage = new MatchDataIndexPage(driver);
		 matchdataVerification = new MatchDataVerification(driver);
		 
		 
		 
	}
	
	public static String getEndPointUriGetTokenIs() throws IOException {
		String endPointURIIs= TestData.getValueFromConfig("config.properties", "EndPointURL");
		String subDomainIs = TestData.getValueFromConfig("config.properties", "SubDomain");
		URIIs = endPointURIIs+"/GetToken?subDomain="+subDomainIs;
		System.out.println(" API URI Checking is : "+URIIs);
		 return URIIs;
		}
	
	public static String getEndPointUriCleanseMatch() throws IOException {
		String endPointURIIs= TestData.getValueFromConfig("config.properties", "EndPointURL");
		String subDomainIs = TestData.getValueFromConfig("config.properties", "SubDomain");
		URIIs = endPointURIIs+"/CleanseMatch/V1.0/?subDomain="+subDomainIs;
		System.out.println(" API URI Checking is : "+URIIs);
		 return URIIs;
		}

}
