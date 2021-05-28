package com.MBS.Init;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import com.MBS.Utility.TestData;

public class URL {

	private static Logger log = LogManager.getLogger(URL.class.getName());
	public String testName = "";
	public static String testUri = "";
	public static String portaltestUrl = "";
	public static String browserName = "";
	public static String osName = "";
	public String HomeDir="";
	public static String browserVersion = "";
	public static String browseruse = "";
	protected String targetBrowser;
	public static String URIIs ="";
    protected static WebDriver driver;

    
	public static String getEndPoint() {
		log.info("Base URL : "+testUri);
		return testUri;
	}

	public static String getEndPoint(String resources) {
		log.info("URL EndPoint : "+testUri + resources);
		return testUri + resources;
	}
	
	public void logStep(int msg1, String msg2) 
	{
		//Reporter.log("Step-"+msg1+" : "+msg2 + "</br>");
		Reporter.log(msg2 + "</br>");
		System.out.println("Step-"+msg1+" : "+msg2);// for jenkins  
	}
	
	public static void log(String msg) 
	{
		Reporter.log(msg + "</br>");
		System.out.println(msg);
	}
	
	public static String getEndPointUriGetTokenIs() throws IOException {
		String endPointURIIs= TestData.getValueFromConfig("config.properties", "EndPointURL");
		String subDomainIs = TestData.getValueFromConfig("config.properties", "SubDomain");
		URIIs = endPointURIIs+"/GetToken?subDomain="+subDomainIs;
		System.out.println(" API URI Checking is Generate Token : "+URIIs);
		 return URIIs;
		}
	
	public static String getEndPointUriCleanseMatchCall() throws IOException {

		String endPointURIIs= TestData.getValueFromConfig("config.properties", "EndPointURL");
	//	String subDomainIs = TestData.getValueFromConfig("config.properties", "SubDomain");
		URIIs = endPointURIIs+"/CleanseMatch/V1.0/?";
		log("API URI Checking is CLeanse Match Call:"+URIIs);

		return  URIIs;	
		}
	
	public static String getEndPointUriCleansMatchEnrichmentCall() throws IOException {

		String endPointURIIs= TestData.getValueFromConfig("config.properties", "EndPointURL");
	//	String subDomainIs = TestData.getValueFromConfig("config.properties", "SubDomain");
		URIIs = endPointURIIs+"/CleanseMatchEnrichment/V1.0/?";
		log("API URI Checking is :"+URIIs);

		return  URIIs;	
		}
	
	public static String getEndPointUriRefreshDunsNumber() throws IOException {

		String endPointURIIs= TestData.getValueFromConfig("config.properties", "EndPointURL");
	//	String subDomainIs = TestData.getValueFromConfig("config.properties", "SubDomain");
		URIIs = endPointURIIs+"/RefreshDUNS?";
		log("API URI Checking is :"+URIIs);

		return  URIIs;	
		}
	
	public static String postUploadData() throws IOException {

		String endPointURIIs= TestData.getValueFromConfig("config.properties", "EndPointURL");
		URIIs = endPointURIIs+"/Upload";
		log("API URI Checking for Upload Is :"+URIIs);

		return  URIIs;	
		}
	
}
