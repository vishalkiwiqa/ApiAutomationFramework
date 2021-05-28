package com.MBS.Init;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import com.MBS.Utility.TestData;

public class SeleniumInit{
	public String suiteName = "";
	public String testName = "";
	public static String PayertestURL="";
	public static String testUrl;
	public static String seleniumHub; // Selenium hub IP
	public static String seleniumHubPort; // Selenium hub port
	protected String targetBrowser; // Target browser
	protected static String test_data_folder_path = null;
	public static String currentWindowHandle = "";// Get Current Window handle
	public static String browserName = "";
	public static String osName = "";
	public String HomeDir="";
	public static String browserVersion = "";
	public static String browseruse = "";
	public static String Url="";
	public static String AuthorName;
	public static String ModuleName;
	public static String Version="";
	/*public static String header="";*/
	public static int col=0;

	protected static String screenshot_folder_path = null;
	public static String currentTest; // current running test
	protected static Logger logger = Logger.getLogger("testing");
	protected static WebDriver driver;
   
	@SuppressWarnings("deprecation")
	@BeforeSuite(alwaysRun = true)
	public void fetchSuite(ITestContext testContext) throws IOException
	{
		
		 String execution=testContext.getCurrentXmlTest().getParameter("ExecutionByXML");
		 try
		 {
			 TestData.deletePastScreenshots(System.getProperty("user.dir") +"\\test-output\\screenshots");
		 }catch(Exception e)
		 {}
		 TestData.clearProperties("uploadConfig.properties");
		 TestData.setValueConfig("uploadConfig.properties","downloadZip", "");  
		 TestData.setValueConfig("uploadConfig.properties","MatchOutPut", "");
		 TestData.setValueConfig("uploadConfig.properties","LowConfidence", "");
		 TestData.setValueConfig("uploadConfig.properties","TrasferedDuns", "");
		 TestData.setValueConfig("uploadConfig.properties","NoMatchQueue", "");
		 TestData.setValueConfig("uploadConfig.properties","ActiveQueueOutput", "");
		 TestData.setValueConfig("uploadConfig.properties","EnrichmentCount", "");
		 TestData.setValueConfig("uploadConfig.properties","ExportedFile", "");
		if(execution.equalsIgnoreCase("true"))
		{
			testUrl=testContext.getCurrentXmlTest().getParameter("URL");
			targetBrowser =testContext.getCurrentXmlTest().getParameter("Browser");
		}else
		{
			testUrl=TestData.getValueFromConfig("config.properties", "URL");
			targetBrowser =TestData.getValueFromConfig("config.properties","Browser");
		}
		
		browserName=targetBrowser;
		
		//URL remote_grid = new URL("http://" + seleniumHub + ":" + seleniumHubPort + "/wd/hub");
		String SCREENSHOT_FOLDER_NAME = "screenshots";
		String TESTDATA_FOLDER_NAME = "test_data";
		test_data_folder_path = new File(TESTDATA_FOLDER_NAME).getAbsolutePath();
		screenshot_folder_path = new File(SCREENSHOT_FOLDER_NAME).getAbsolutePath();
		try{
		DesiredCapabilities capability = null;		
		if (targetBrowser == null || targetBrowser.contains("firefox") || targetBrowser.equalsIgnoreCase("firefox")) 
		{
			File driverpath = new File("Resource/geckodriver.exe");
			String path1 = driverpath.getAbsolutePath();
			System.setProperty("webdriver.gecko.driver",path1);
			
			capability = DesiredCapabilities.firefox();
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("browser.download.folderList", 2);
			profile.setPreference("browser.download.manager.showWhenStarting",false);
			profile.setPreference("download.default_directory",
	                new File("DownloadData").getAbsolutePath());
			 profile.setPreference("browser.helperApps.neverAsk.saveToDisk", 
			     "text/xlsx"); 

//			 profile.setPreference( "pdfjs.disabled", true );
			capability.setJavascriptEnabled(true);
			osName = System.getProperty("os.name");
			HomeDir=System.getProperty("user.home");
			capability.setCapability("marionette", true);
			driver= new FirefoxDriver(capability);
			
			
			//driver = new RemoteWebDriver(remote_grid, capability);
		}else if (targetBrowser.contains("Edge")||targetBrowser.equalsIgnoreCase("Edge"))
		{
			capability = DesiredCapabilities.internetExplorer();
			File driverpath = new File("Resource/msedgedriver.exe");
			String path1 = driverpath.getAbsolutePath();
			System.setProperty("webdriver.edge.driver",path1 );
			capability.setBrowserName("Microsoft Edge");
			/*WebDriverManager manager = WebDriverManager.edgedriver();
			manager.config().setEdgeDriverVersion("84.0.522.49");
			manager.setup();*/
			//EdgeOptions options = new EdgeOptions();
			//capability.setJavascriptEnabled(true);
			osName = capability.getPlatform().name();
			driver= new EdgeDriver();
			//driver = new RemoteWebDriver(remote_grid, capability);
		}
		else if (targetBrowser.contains("ie11")||targetBrowser.equalsIgnoreCase("ie11"))
		{
			capability = DesiredCapabilities.internetExplorer();
			File driverpath = new File("Resource/IEDriverServer.exe");
			String path1 = driverpath.getAbsolutePath();
			System.setProperty("webdriver.ie.driver",path1 );
			capability.setBrowserName("internet explorer");
			capability.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
			capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capability.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
			capability.setCapability("nativeEvents", false);
			capability.setJavascriptEnabled(true);
			osName = capability.getPlatform().name();
			driver= new InternetExplorerDriver(capability);
			//driver = new RemoteWebDriver(remote_grid, capability);
		}else if (targetBrowser.contains("chrome") || targetBrowser.equalsIgnoreCase("chrome"))
		{
			capability = DesiredCapabilities.chrome();
			File driverpath = new File("Resource/chromedriver.exe");
			String path1 = driverpath.getAbsolutePath();
			System.setProperty("webdriver.chrome.driver",path1);
			final ChromeOptions chromeOptions = new ChromeOptions();
			Map<String, Object> prefs = new HashMap<String, Object>();
			 prefs.put("download.default_directory",
		                new File("DownloadData").getAbsolutePath());
			chromeOptions.setExperimentalOption("prefs", prefs);
			capability.setBrowserName("chrome");
			capability.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
			capability.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
			//capability.setCapability("disable-popup-blocking", true);
			osName = capability.getPlatform().name();
			capability.setJavascriptEnabled(true);
			osName = capability.getPlatform().name();
			browserVersion = capability.getVersion();
			//driver = new RemoteWebDriver(remote_grid, capability);
			driver= new ChromeDriver(capability);
		}else if (targetBrowser.contains("safari"))
		{
			capability = DesiredCapabilities.safari();
			capability.setJavascriptEnabled(true);
			capability.setBrowserName("safari");
			driver = new SafariDriver(capability);
		}
		
		//suiteName = testContext.getSuite().getName();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(testUrl);
		
		
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@BeforeTest(alwaysRun = true)
	public void fetchSuiteConfiguration(ITestContext testContext) throws IOException 
	{
		/*seleniumHub = testContext.getCurrentXmlTest().getParameter("selenium.host");
		seleniumHubPort = testContext.getCurrentXmlTest().getParameter("selenium.port");*/
		//testUrl=TestData.getValueFromConfig("config.properties","URL");
	}
	/**
	 * WebDriver initialization
	 * @return WebDriver object
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@BeforeMethod(alwaysRun = true)
	public void setUp(Method method, ITestContext testContext,ITestResult testResult) throws IOException, InterruptedException 
	{
		
		driver.get(testUrl);
		
		suiteName = testContext.getSuite().getName();
		
		 
	}
	/**
	 * After Method
	 * 
	 * @param testResult
	 */
	@AfterMethod(alwaysRun = true)
	public void tearDown(ITestResult testResult,ITestContext testContext)
	{
		String screenshotName="";
		testName = testResult.getName();
		
		try 
		{
			screenshotName = Common.getCurrentTimeStampString(); //+ testName;
			if (!testResult.isSuccess()) 
			{
				/* Print test result to Jenkins Console */
				System.out.println();
				System.out.println("TEST FAILED - " + testName);
				System.out.println();
				System.out.println("ERROR MESSAGE: " + testResult.getThrowable());
				System.out.println("\n");
				Reporter.setCurrentTestResult(testResult);
				/* Make a screenshot for test that failed */
				if(testResult.getStatus()==ITestResult.FAILURE)
				{ 
					System.out.println("1 message from tear down");
					log("Please look to the screenshot :- "+ Common.makeScreenshot(driver, screenshotName));
		        }
			}
		}catch (Throwable throwable)
		{
		System.out.println(throwable);
		}
	}
	/**
	 * Log given message to Reporter output.
	 * 
	 * @param msg
	 *            Message/Log to be reported.
	 */
	@AfterSuite(alwaysRun = true)
	public void postConfigue()
	{
		driver.manage().deleteAllCookies();
		driver.close();
	}
	public void log(String msg) 
	{
		Reporter.log(msg + "</br>");
		System.out.println(msg);
	}
	public void logStep(int msg1, String msg2) 
	{
		//Reporter.log("Step-"+msg1+" : "+msg2 + "</br>");
		Reporter.log(msg2 + "</br>");
		System.out.println("Step-"+msg1+" : "+msg2);// for jenkins  
	}
	public void logCase(String msg)
	{
		Reporter.log("Test Case : "+msg+"</br>");
		System.out.println("Test Case : "+msg);
	}
	public static void slog(String msg)
	{
		Reporter.log(msg + "</br>");
		System.out.println(msg);
	}
	
	public void logStatus(final int test_status,String msg) 
	{
		switch (test_status) 
		{
			case ITestStatus.PASSED:
				//test.log(Status.PASS,"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp "+msg+" ");
				log(msg+" <Strong><font color=#008000>Pass</font></strong>");
				break;
			case ITestStatus.FAILED:
				String screenshotName = Common.getCurrentTimeStampString();
				log(msg+" <Strong><font color=#FF0000>Fail</font></strong> -> Please look to the screenshot :- "+ Common.makeScreenshot(driver, screenshotName));
				//MakeScreenshots();
				break;
			case ITestStatus.SKIPPED:
				log(msg);
				break;
			default:
				break;
		}
	}
	
	public void MakeScreenshots()
	{
		String screenshotName = Common.getCurrentTimeStampString() + testName;
		Common.makeScreenshot2(driver, screenshotName);
	}
}