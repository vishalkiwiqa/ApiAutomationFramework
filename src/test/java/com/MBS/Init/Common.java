package com.MBS.Init;

import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.aventstack.extentreports.Status;

/*import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
*/
/**
 * Define Common Webdriver
 */
public class Common 
{
	Date date = new Date();
	protected static Wait<WebDriver> wait;
	public static String alerttext;
	public static com.aventstack.extentreports.ExtentTest test;

	/**
	 * Find web-element for given locator.
	 * 
	 * @param elementName
	 * @return
	 */
	
	public static WebElement findElement(WebDriver driver, String elementName) 
	{
		String locator;
		locator = elementName;
		int count = 0;
		while (count < 4) 
		{
			try 
			{
				if (locator.startsWith("link=") || locator.startsWith("LINK=")) 
				{
					locator = locator.substring(5); // remove "link=" from
					try 
					{
						if (locator.contains(" "))
							return driver.findElement(By.partialLinkText(locator));

						return driver.findElement(By.linkText(locator));
					} 
					catch (Exception e) 
					{
						return null;
					}
				}
				if (locator.startsWith("id=")) 
				{
					locator = locator.substring(3); // remove "id=" from locator
					try 
					{
						return driver.findElement(By.id(locator));
					} 
					catch (Exception e) 
					{
						return null;
					}
				} 
				else if (locator.startsWith("//")) 
				{
					try 
					{
						return driver.findElement(By.xpath(locator));
					} 
					catch (Exception e) 
					{
						return null;
					}
				} 
				else if (locator.startsWith("css=")) 
				{
					locator = locator.substring(4); // remove "css=" from
													// locator
					try 
					{
						return driver.findElement(By.cssSelector(locator));
					} 
					catch (Exception e) 
					{
						return null;
					}
				} 
				else if (locator.startsWith("name=")) 
				{
					locator = locator.substring(5); // remove "name=" from
													// locator
					try 
					{
						return driver.findElement(By.name(locator));
					} 
					catch (Exception e) 
					{
						return null;
					}
				} 
				else 
				{
					try 
					{
						return driver.findElement(By.id(locator));
					} 
					catch (Exception e) 
					{
						return null;
					}
				}
			} 
			catch (StaleElementReferenceException e) 
			{
				e.toString();
				count = count + 1;
			}
			count = count + 4;
		}
		return null;
	}

	public static void moveToObjectelement(WebDriver driver, String xpath) 
	{
		driver.switchTo().frame(driver.findElement(By.xpath(xpath)));
	}

	public static String getNextDate(String Fortmat, int CurrentToNextDate)
	{
		Date date = new Date(System.currentTimeMillis());
	    SimpleDateFormat formatter = new SimpleDateFormat(Fortmat);
	    Calendar c = Calendar.getInstance(); 
	    c.setTime(date); 
	    c.add(Calendar.DATE, CurrentToNextDate);
	    date = c.getTime();
	    String s1 = formatter.format(date);
	    
	    return s1;
	}
	
	/**
	 * Perform horizontal scrolling
	 * 
	 * @param driver
	 * @param element
	 */
	public static void scrollUpToBottom(WebDriver driver)
	{
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}
	public static void scrollUpToElement(WebDriver driver,WebElement element)
	{
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}
	
	public static void scrollUpTop(WebDriver driver)
	{
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.scrollTo(0, 0)");
	}
	
	public static void scrollToHorizontal(WebDriver driver, WebElement element) 
	{
		Actions dragger = new Actions(driver);
		WebElement draggablePartOfScrollbar = element;
		// drag downwards
		int numberOfPixelsToDragTheScrollbarDown = 50;
		for (int i = 10; i < 500; i = i + numberOfPixelsToDragTheScrollbarDown) 
		{
			try 
			{
				// this causes a gradual drag of the scroll bar, 10 units at a
				// time
				dragger.moveToElement(draggablePartOfScrollbar).clickAndHold()
						.moveByOffset(numberOfPixelsToDragTheScrollbarDown, 0).release().perform();
				Thread.sleep(1000L);
			} 
			catch (Exception e1) 
			{
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Perform vertical scrolling
	 * 
	 * @param driver
	 * @param element
	 */
	//js executor 
	public static void scrollToVertical(WebDriver driver, WebElement element) 
	{
		Actions dragger = new Actions(driver);
		WebElement draggablePartOfScrollbar = element;
		// drag downwards
		int numberOfPixelsToDragTheScrollbarDown = 50;
		for (int i = 10; i < 500; i = i + numberOfPixelsToDragTheScrollbarDown) 
		{
			try 
			{
				// this causes a gradual drag of the scroll bar, 10 units at a
				// time
				dragger.moveToElement(draggablePartOfScrollbar).clickAndHold()
						.moveByOffset(0, numberOfPixelsToDragTheScrollbarDown).release().perform();
				Thread.sleep(1000L);
			} 
			catch (Exception e1) 
			{
				e1.printStackTrace();
			}
		}
	}

	public static void logcase(String msg) 
	{
		System.out.println(msg);
		Reporter.log("<strong> <h3 style=\"color:DarkViolet\"> " + msg + "</h3> </strong>");
	}

	public static void logstep(String msg) 
	{
		System.out.println(msg);
		Reporter.log("<strong>" + msg + "</strong></br>");
	}

	/**
	 * Checks checkbox or toggle element.
	 * 
	 * @param element
	 *            Checkbox element.
	 */
	public static void checkChkBox(WebElement element) 
	{
		boolean isCheckBoxSet;
		isCheckBoxSet = element.isSelected();
		if (!isCheckBoxSet) 
		{
			element.click();
		}
	}

	public static void movetoalertAndAccept(WebDriver webDriver) 
	{
		try 
		{
			waitForAlert(webDriver);
			Alert alert = webDriver.switchTo().alert();
			alerttext = alert.getText();
			System.out.println("alert----:"+alerttext);
			alert.accept();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	public static String getalertText(WebDriver webDriver) 
	{
		try 
		{
			Alert alert = webDriver.switchTo().alert();
			alerttext= alert.getText();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return alerttext;
	}
	public static void waitForAlert(WebDriver driver)
	{
	   int i=0;
	   while(i++<5)
	   {
	        try
	        {
				driver.switchTo().alert();
	            break;
	        }
	        catch(Exception e)
	        {
	         pause(2);
	          continue;
	        }
	   }
	}
	public static String getBeforeAndAfterDateMonthWiseForSingleDigit(int i) {
		Calendar cur = Calendar.getInstance();
		NumberFormat f = new DecimalFormat("0");
		cur.add(Calendar.MONTH, i);
		return f.format((cur.get(Calendar.MONTH)) + 1) + "/" + f.format(cur.get(Calendar.DATE)) + "/"
				+ cur.get(Calendar.YEAR);
	}
	
	public static void AcceptUnhandledAlert(WebDriver webDriver) 
	{
		try 
		{
			WebDriverWait wait = new WebDriverWait(webDriver, 2);
	        wait.until(ExpectedConditions.alertIsPresent());
			Alert alert = webDriver.switchTo().alert();
			log("Alert Message: "+alert.getText());
			alert.accept();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	public static int randomBetween(int minimum,int maximum) 
	{
		return new Random().nextInt(maximum - minimum + 1) + minimum;
	}
	/**
	 * Open Mailinator
	 * 
	 * @param emailAddress
	 */
	public static void openMailinator(WebDriver driver, String emailAddress) 
	{
		pause(3);
		String emailParsed[] = emailAddress.split("@");
		String url = "http://" + emailParsed[0] + ".mailinator.com";
		goToUrl(driver, url);
	}

	/**
	 * Gets current time in the following format Month, Date, Hours, Minutes,
	 * Seconds, Millisecond.
	 * 
	 * @return Current date.
	 */
	public static String getCurrentTimeStampString() 
	{
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmssSS");
		TimeZone timeZone = TimeZone.getDefault();
		Calendar cal = Calendar.getInstance(new SimpleTimeZone(timeZone.getOffset(date.getTime()), "IST"));
		sd.setCalendar(cal);
		return sd.format(date);
	}
	
	public static String getCurrentTimeStampString2() 
	{
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
		TimeZone timeZone = TimeZone.getDefault();
		Calendar cal = Calendar.getInstance(new SimpleTimeZone(timeZone.getOffset(date.getTime()), "IST"));
		sd.setCalendar(cal);
		return sd.format(date);
	}
	
	/**
	 * Takes screenshot and adds it to TestNG report.
	 * 
	 * @param driver
	 *            WebDriver instance.
	 */
	public static String makeScreenshot(WebDriver driver, String screenshotName) 
	{
		WebDriver augmentedDriver = new Augmenter().augment(driver);
		/* Take a screenshot */
		File screenshot = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
		String nameWithExtention = screenshotName + ".png";
		/* Copy screenshot to specific folder */
		try 
		{
			String reportFolder = "test-output" + File.separator;
			String screenshotsFolder = "screenshots";
			File screenshotFolder = new File(reportFolder + screenshotsFolder);
			if (!screenshotFolder.getAbsoluteFile().exists()) 
			{
				screenshotFolder.mkdir();
			}
			FileUtils.copyFile(screenshot,
					new File(screenshotFolder + File.separator + nameWithExtention).getAbsoluteFile());
		} 
		catch (IOException e) 
		{
			log("Failed to capture screenshot: " + e.getMessage());
		}
		//log(getScreenshotLink(nameWithExtention, nameWithExtention));
		return getScreenshotLink(nameWithExtention, nameWithExtention);
	}
	
	public static void makeScreenshot2(WebDriver driver, String screenshotName) 
	{
		WebDriver augmentedDriver = new Augmenter().augment(driver);
		/* Take a screenshot */
		File screenshot = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
		String nameWithExtention = screenshotName + ".png";
		/* Copy screenshot to specific folder */
		try 
		{
			String reportFolder = "test-output" + File.separator;
			String screenshotsFolder = "screenshots";
			File screenshotFolder = new File(reportFolder + screenshotsFolder);
			if (!screenshotFolder.getAbsoluteFile().exists()) 
			{
				screenshotFolder.mkdir();
			}
			FileUtils.copyFile(screenshot,
					new File(screenshotFolder + File.separator + nameWithExtention).getAbsoluteFile());
		} 
		catch (IOException e) 
		{
			new URL();
			Reporter.log("Failed to capture screenshot: " + e.getMessage());
			//SeleniumInit.test.log(null, "Failed to capture screenshot: " + e.getMessage());
		}
		log("<b>Please look to the screenshot - </b>"+getScreenshotLink2(nameWithExtention, nameWithExtention)+"<br>");
	}

	/**
	 * Log given message to Reporter output.
	 * 
	 * @param msg
	 *            Message/Log to be reported.
	 */
	public static void log(String msg) 
	{
		System.out.println(msg);
		Reporter.log(msg + "</br>");
	}

	public static void logStatus(String Status) 
	{
		System.out.println(Status);
		if (Status.equalsIgnoreCase("Pass")) 
		{
			log("<br><Strong><font color=#008000>Pass</font></strong></br>");
		} 
		else if (Status.equalsIgnoreCase("Fail")) 
		{
			log("<br><Strong><font color=#FF0000>Fail</font></strong></br>");
		}
	}

	/**
	 * Generates link for TestNG report.
	 * 
	 * @param screenshot_name
	 *            Screenshot name.
	 * @param link_text
	 *            Link text.
	 * @return Formatted link for TestNG report.
	 */
	public static String getScreenshotLink(String screenshot_name, String link_text) 
	{
		/*String dataFilePath = "test-output/screenshots";
		File datafile = new File(dataFilePath);
		String fullpath = datafile.getAbsolutePath();*/
		//log("<br><Strong><font color=#FF0000>--Failed</font></strong>");
		return "<a href='../test-output/screenshots/" + screenshot_name + "' target='_new'>" + link_text + "</a>";
	}
	
	public static String getScreenshotLink2(String screenshot_name, String link_text) 
	{
		String dataFilePath = "test-output/screenshots";
		File datafile = new File(dataFilePath);
		String fullpath = datafile.getAbsolutePath();
		return "<a href='" + fullpath + "/" + screenshot_name + "' target='_new'>" + link_text + "</a>";
	}

	/**
	 * Checks whether the needed WebElement is displayed or not.
	 * 
	 * @param element
	 *            Needed element
	 * 
	 * @return true or false.
	 */
	public static boolean isElementDisplayed(WebElement element) 
	{
		try 
		{
			return element.isDisplayed();
		} 
		catch(NoSuchElementException f ) {
			return false;
		}
		catch (Exception e ) 
		{
			return false;
		}
		
	}
	public static boolean isElemenEnabled(WebElement element) 
	{
		try 
		{
			return element.isEnabled();
		} 
		catch (Exception e) 
		{
			return false;
		}
	}

	public static boolean isElementNotDisplayed(WebElement element) 
	{
		try 
		{
			return !element.isDisplayed();
		} 
		catch (Exception e) 
		{
			return false;
		}
	}

	/**
	 * Wait(max. 1 minute) till given element does not disappear from page.
	 * 
	 * @param by
	 *            Locator of element.
	 * @return
	 * @throws InterruptedException
	 */
	public static boolean waitForElementIsDisplayed(WebElement by) throws InterruptedException 
	{
		for (int second = 0;; second++) 
		{
			if (second >= 60) 
			{
				break;
			}
			try 
			{
				if (isElementDisplayed(by))
					break;
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			pause(1);
		}
		return false;
	}

	/**
	 * Checks if given elements is checked.
	 * 
	 * @param locator
	 *            Locator of element.
	 * 
	 * @return true if checked else false.
	 */
	public static boolean isChecked(WebDriver driver, String locator) 
	{
		return findElement(driver, locator).isSelected();
	}

	/**
	 * Checks whether the needed WebElement is displayed or not.
	 * 
	 * @param elementLocator
	 * @return
	 */
	public static boolean isElementDisplayed(WebDriver driver, By elementLocator) 
	{
		try 
		{
			return driver.findElement(elementLocator).isDisplayed();
		} 
		catch (Exception e) 
		{
			return false;
		}
	}

	/**
	 * Set data in to clipboard
	 * 
	 * @param string
	 */
	public static void setClipboardData(String string) 
	{
		StringSelection stringSelection = new StringSelection(string);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
	}

	/**
	 * Checks whether the visibility of Element Located
	 * 
	 * @param by
	 * @return
	 */
	public static ExpectedCondition<WebElement> visibilityOfElementLocated(final By by) 
	{
		return new ExpectedCondition<WebElement>() 
		{
			public WebElement apply(WebDriver driver) 
			{
				WebElement element = driver.findElement(by);
				return element.isDisplayed() ? element : null;
			}
		};
	}

	/**
	 * Wait up to String locator present
	 * 
	 * @param selector
	 */
	public static void waitForElement1(WebDriver driver, String xpath) 
	{
		wait = new WebDriverWait(driver, 10);
		try 
		{
			wait.until(visibilityOfElementLocated(By.xpath(xpath)));
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	public static void waitForElement(WebElement webelement, WebDriver driver) 
	{
		//long starttime=System.currentTimeMillis();
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOf(webelement));
		//long endtime=System.currentTimeMillis()-starttime;
		/*SeleniumInit.ExplicitWait=SeleniumInit.ExplicitWait+endtime;
		System.out.println(" visibility of wait --> "+SeleniumInit.ExplicitWait);*/
	}
	
	public static void waitForInvisibility(By locator, WebDriver driver) 
	{
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}
	
	public static void PresenceOfElement(By locator, WebDriver driver) 
	{
		//long starttime=System.currentTimeMillis();
		(new WebDriverWait(driver, 20)).until(ExpectedConditions.presenceOfElementLocated(locator));
		//long endtime=System.currentTimeMillis()-starttime;
		/*SeleniumInit.ExplicitWait=SeleniumInit.ExplicitWait+endtime;
		System.out.println(" Presence of Element wait --> "+SeleniumInit.ExplicitWait);*/
	}
	
	public static void clickableElement(By locator, WebDriver driver) 
	{
		//long starttime=System.currentTimeMillis();
		(new WebDriverWait(driver, 20)).until(ExpectedConditions.elementToBeClickable(locator));
		//long endtime=System.currentTimeMillis()-starttime;
		/*SeleniumInit.ExplicitWait=SeleniumInit.ExplicitWait+endtime;
		System.out.println(" clickable Element (By locator)  wait --> "+SeleniumInit.ExplicitWait);*/
	}
	public static void clickableElement(WebElement webelement, WebDriver driver) 
	{
		//long starttime=System.currentTimeMillis();
		(new WebDriverWait(driver, 20)).until(ExpectedConditions.elementToBeClickable(webelement));
		//long endtime=System.currentTimeMillis()-starttime;
		/*SeleniumInit.ExplicitWait=SeleniumInit.ExplicitWait+endtime;
		System.out.println(" clickable Element (By element) wait --> "+SeleniumInit.ExplicitWait);*/
	}
	public static void waitForIframe(String str, WebDriver driver) 
	{
		(new WebDriverWait(driver, 20)).until(ExpectedConditions.visibilityOfElementLocated((By
                .cssSelector(str))));
	}
	public static void switchToIframeByCss(String str, WebDriver driver)
	{
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.visibilityOfElementLocated((By
                .cssSelector(str))));
		driver.switchTo().frame(driver.findElement(By.cssSelector(str)));
	}
	/**
	 * Finds handle to second window other than given handle to current window
	 * and switches to as well.
	 * 
	 * @param handleCurrentWindow
	 * @return handleSecondWindow
	 */
	public static String findAndSwitchToSecondWindow(WebDriver driver, String handleCurrentWindow) 
	{
		pause(3);
		Set<String> windows = driver.getWindowHandles();
		String handleSecondWindow = null;
		for (String window : windows) 
		{
			if (!window.contains(handleCurrentWindow)) 
			{
				handleSecondWindow = window;
			}
		}
		// Switch to the second window.
		try 
		{
			pause(4);
			driver.switchTo().window(handleSecondWindow);
		} 
		catch (Throwable failure) 
		{ 	
			// If there is problem in switching
			// window, then re-try.
			pause(3);
			driver.switchTo().window(handleSecondWindow);
		}
		return handleSecondWindow;
	}
	 
	public static String findAndSwitchToSecondWindow(WebDriver driver, String handleCurrentWindow,String handleCurrentWindow1) 
	{
		pause(3);
		Set<String> windows = driver.getWindowHandles();
		String handleSecondWindow = null;
		for (String window : windows) 
		{
			if (!window.contains(handleCurrentWindow)&&!window.contains(handleCurrentWindow1)) 
			{
				handleSecondWindow = window;
			}
		}
		// Switch to the second window.
		try 
		{
			pause(4);
			driver.switchTo().window(handleSecondWindow);
		} 
		catch (Throwable failure) 
		{ 
			// If there is problem in switching
			// window, then re-try.
			pause(3);
			driver.switchTo().window(handleSecondWindow);
		}
		return handleSecondWindow;
	}
	public static void closeOtherWindow(WebDriver driver, String handleCurrentWindow) 
	{
		Set<String> windows = driver.getWindowHandles();
		for (String window : windows) 
		{
			if (!window.contains(handleCurrentWindow)) 
			{
				driver.close();
			}
		}
		driver.switchTo().window(handleCurrentWindow);
	}
	
	public static String findAndSwitchToSecondWindows(WebDriver driver, String handleCurrentWindow,String AnotherWindow) 
	{
		pause(3);
		Set<String> windows = driver.getWindowHandles();
		String handleSecondWindow = null;
		for (String window : windows) 
		{
			if (!window.contains(handleCurrentWindow) && !window.contains(AnotherWindow)) 
			{
				handleSecondWindow = window;
			}
		}
		try 
		{
			pause(4);
			driver.switchTo().window(handleSecondWindow);
		} 
		catch (Throwable failure) 
		{ 
			// If there is problem in switching
			pause(3);
			driver.switchTo().window(handleSecondWindow);
		}
		return handleSecondWindow;
	}
	
	public static String findAndSwitchToSecondWindows(WebDriver driver, String handleCurrentWindow,
			String AnotherWindow,String otherWindow) 
	{
		pause(3);
		Set<String> windows = driver.getWindowHandles();
		String handleSecondWindow = null;
		for (String window : windows) 
		{
			if (!window.contains(handleCurrentWindow) && !window.contains(AnotherWindow)) 
			{
				handleSecondWindow = window;
			}
		}
		try 
		{
			pause(4);
			driver.switchTo().window(handleSecondWindow);
		} 
		catch (Throwable failure) 
		{ 
			// If there is problem in switching
			pause(3);
			driver.switchTo().window(handleSecondWindow);
		}
		return handleSecondWindow;
	}

	/**
	 * Select data from dropwon or combobox by Value.
	 * 
	 * @param element
	 * @param value
	 */
	public static void selectFromCombo(WebElement element, String value) 
	{
		Select select = new Select(element);
		select.selectByValue(value);
	}

	/**
	 * Select data form dropdown or combobox by visible element
	 * 
	 * @param element
	 * @param value
	 */
	public static void selectFromComboByVisibleElement(WebElement element, String value) 
	{
		Select select = new Select(element);
		select.selectByVisibleText(value);
	}
	public static void selectFromComboByIndex(WebElement element, int index) 
	{
		Select select = new Select(element);
		select.selectByIndex(index);
	}
	public static String selectFromComboByIndexReturnValue(WebElement element, int index) 
	{
		Select select = new Select(element);
		select.selectByIndex(index);
		Common.pause(1);
		return select.getFirstSelectedOption().getText();
	}
	public static String selectFromComboByIndexReturnValue(WebElement element) 
	{
		Select select = new Select(element);
		return select.getFirstSelectedOption().getText();
	}
	public static String getFridayDateOfParticularMonth(String year,String month)
	{
		 Calendar cal = Calendar.getInstance();
		   cal.set( Integer.parseInt(year), Integer.parseInt(month), 1 );
		   cal.add( Calendar.DAY_OF_MONTH, -( cal.get( Calendar.DAY_OF_WEEK ) % 7 + 1 ) );
		   SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		   Date d1=cal.getTime();
		   return format.format(d1);
	}

	/*public static int getNumberOfDayBetweenTwoDates(String StartDate,String EndDate,int day)
	{
		Date start= new Date(StartDate);
		Date end= new Date(EndDate);
		int current=start.getDay();// 0 refers Sunday and 6 does Saturday 
		int days=0;
		if(current!=0) 
		start.setDate(start.getDate()+(day-current));//set the start date to very first coming Sunday 
		while(start.before(end)){ 
			days++; 
		System.out.println("---->"+start);
		start.setDate(start.getDate()+7); 
		} 
		if(end.getDay()==0) 
		days++; 
		return days;
	}*/

	/**
	 * Wait up to By element present
	 * 
	 * @param element
	 */
	public static void waitForElement(WebDriver driver, By element) 
	{
		try 
		{
			wait = new WebDriverWait(driver, 750);
			wait.until(visibilityOfElementLocated(element));
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * Clicks on visible or not visible element.
	 * 
	 * @param element
	 *            Web element.
	 */
	public static void jsClick(WebDriver driver, WebElement element) 
	{
		highlightElement(driver, element);
		((JavascriptExecutor) driver).executeScript("return arguments[0].click();", element);
		// this.waitForAjax("0");
	}

	/**
	 * Highlight the element and click on same
	 * 
	 * @param driver
	 * @param element
	 */
	public static void clickOn(WebDriver driver, WebElement element) 
	{
		mouseOver(driver, element);
		highlightElement(driver, element);
		element.click();
	}

	/**
	 * Generates random symbols;
	 * 
	 * @param length
	 *            Length of the generated symbols.
	 * 
	 * @return StringBuffer object.
	 */
	public static String generateRandomChars(int length) 
	{
		String random = RandomStringUtils.randomAlphabetic(length);
		return random;
	}

	/**
	 * public static String generateMailinatorID() { String id =
	 * "KiwiTest"+TestData.getRandomDateID()+generateRandomChars(2)+
	 * "@mailinator.com"; return id; }
	 */
	/**
	 * Generate Random Number in Length
	 * 
	 * @param length
	 * @return
	 */
	public static String generateRandomNumber(int length) {
		String numNoRange="";
		//numNoRange = RandomStringUtils.randomNumeric(length);
		do{
			numNoRange = RandomStringUtils.randomNumeric(length);
		}while(numNoRange.length()!=length);
		return numNoRange;
	}
	/**
	 * Mouse Hover in Web element
	 * 
	 * @param element
	 */
	public static void mouseOver(WebDriver driver, WebElement element) 
	{
		Actions builder = new Actions(driver);
		builder.moveToElement(element).build().perform();
	}
	/**
	 * Get text in a given element.
	 * 
	 * @param elementName
	 *            Locator of element.
	 * 
	 * @return text in given element.
	 */
	public static String getText(WebDriver driver, String elementName) 
	{
		String text;
		try 
		{
			text = findElement(driver, elementName).getText();
		} 
		catch (Exception e) 
		{
			text = "Element was not found";
		}
		return text;
	}

	/**
	 * 
	 * Get text in of given Element using JavaScript
	 * 
	 * @param driver
	 * @param element
	 *            webElement
	 * @return
	 */
	public static String getTextJS(WebDriver driver, WebElement element) 
	{
		return (String) ((JavascriptExecutor) driver).executeScript("return jQuery(arguments[0]).text();", element);
	}

	/**
	 * Get value of given element dynamically.
	 * 
	 * @param locator
	 *            Locator of element.
	 * 
	 * @return Dynamic value.
	 */
	public String getValue(WebDriver driver, String locator) 
	{
		return findElement(driver, locator).getAttribute("value");
	}

	/**
	 * Checks if given element is being displayed on page.
	 * 
	 * @param elementName
	 *            Locator of element.
	 * 
	 * @return true if displayed else false.
	 */
	public static boolean isElementDisplayed(WebDriver driver, String elementName) 
	{
		WebElement webElement;
		try 
		{
			webElement = findElement(driver, elementName);
			return webElement.isDisplayed();
		} 
		catch (Exception e) 
		{
			return false;
		}
	}
	
	/**
	 * Wait till given element is present.
	 * 
	 * @param locator
	 *            Locator of element.
	 */
	public static void waitForConditionIsElementPresent(WebDriver driver, String locator) 
	{
		for (int second = 0;; second++) 
		{
			if (second >= 10) 
			{
				break;
			}
			try 
			{
				if (isElementPresent(driver, locator))
					break;
			} 
			catch (Throwable failure) 
			{
				failure.printStackTrace();
			}
			pause(1000);
		}
	}

	/**
	 * Checks if element loaded in browser memory.
	 * 
	 * @param locator
	 *            Locator of element.
	 * @return true if loaded else false.
	 */
	public static boolean isElementPresent(WebDriver driver, String locator) 
	{
		WebElement webElement = findElement(driver, locator);
		if (webElement != null) 
		{
			return true;
		} 
		else 
		{
			return false;
		}
 }

	/**
	 * Pauses for given seconds.
	 * 
	 * @param secs
	 */
	
	public static void pause(int secs) 
	{
		try 
		{
			Thread.sleep(secs * 1000);
		/*	SeleniumInit.tim=SeleniumInit.tim+secs;
			System.out.println(" Pause wait --> "+SeleniumInit.tim);*/
		} 
		catch (InterruptedException interruptedException) 
		{
			interruptedException.printStackTrace();
		}
	}
	public static void Pause(int secs) 
	{
		try 
		{
			Thread.sleep(secs * 1000);
			//Thread.sleep(0 * 1000);
		} 
		catch (InterruptedException interruptedException) 
		{
			interruptedException.printStackTrace();
		}
	}

	/**
	 * Get random numeric of given lenth.
	 * 
	 * @param length
	 *            desired length.
	 * @return
	 */
	public static int randomNumericValueGenerate(int length) 
	{
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(length);
		return randomInt;
	}

	/**
	 * Clears and type new value into given text-box.
	 * 
	 * @param Web
	 *            Element Locator of element.
	 * 
	 * @param value
	 *            New text/value.
	 */
	public static void type(WebElement webElement, String value) 
	{
		webElement.clear();
		webElement.sendKeys(value);
	}
	public static void type(WebDriver driver,WebElement webElement, String value) 
	{
		mouseOver(driver, webElement);
		highlightElement(driver, webElement);
		webElement.sendKeys(value);
	}

	/**
	 * Wait till all ajax calls finish.
	 * 
	 * @param num
	 *            Number of ajax calls to finish.
	 */
	public static void waitForAjax(WebDriver driver, String num) 
	{
		String ajax;
		ajax = ajaxFinised(driver, num);
		for (int second = 0;; second++) 
		{
			if (second >= 20) 
			{
				break;
			} 
			else if (ajax.equals("true")) 
			{
				break;
			}
			try 
			{
				Thread.sleep(1000);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Wait till ajax call finish.
	 * 
	 * @throws InterruptedException
	 */
	public void waitForAjax(WebDriver driver) throws InterruptedException 
	{
		String ajax;
		ajax = ajaxFinised(driver, "1");
		for (int second = 0;; second++) 
		{
			if (second >= 15) 
			{
				break;
			} 
			else if (ajax.equals("true")) 
			{
				break;
			}
			Thread.sleep(1000);
		}
	}

	/**
	 * Checks that all ajax calls are completed on page.
	 * 
	 * @param num
	 *            Number of ajax calls to wait for completion.
	 * 
	 * @return "true" if completed else "false".
	 */
	public static String ajaxFinised(WebDriver driver, String num) 
	{
		Object isAjaxFinished;
		JavascriptExecutor js = (JavascriptExecutor) driver;
		isAjaxFinished = js.executeScript("return jQuery.active == " + num);
		return isAjaxFinished.toString();
	}

	/**
	 * Select Random String From Combobox.
	 * 
	 * @param by
	 * @param driver
	 * @return selected random string
	 * @throws InterruptedException
	 */
	public static String selectRandomOptionFromCombo(By by, WebDriver driver) throws InterruptedException 
	{
		String selectedOption = "";
		WebElement selectCombo = driver.findElement(by);
		Thread.sleep(2);
		List<WebElement> getAllOption = selectCombo.findElements(By.tagName("option"));
		ArrayList<String> arrayOfAllOption = new ArrayList<String>();
		for (WebElement ele : getAllOption) 
		{
			if (!ele.getText().startsWith("Select")) 
			{
				arrayOfAllOption.add(ele.getText());
			}
		}
		int index = new Random().nextInt(arrayOfAllOption.size());
		if (Integer.signum(index) == -1) 
		{
			index = -index;
			// index=Math.abs(index);
		}
		selectedOption = arrayOfAllOption.get(index);
		return selectedOption;
	}

	/**
	 * Get Total Number Of Elements
	 * 
	 * @param driver
	 * @param by
	 * @return interger number of total elements
	 */
	public static int getNumOfElements(WebDriver driver, By by) 
	{
		int i = 0;
		List<WebElement> ele = driver.findElements(by);
		i = ele.size();
		return i;
	}

	/**
	 * Refresh Current Page
	 * 
	 * @param driver
	 */
	public static void refresh(WebDriver driver) 
	{
		driver.navigate().refresh();
	}

	/**
	 * Open URL in New Window
	 * 
	 * @param driver
	 * @param url
	 */
	public static void openUrlInNewTab(WebDriver driver, String url) 
	{
		if (System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) 
		{
			driver.findElement(By.tagName("body")).sendKeys(Keys.COMMAND + "t");
		} 
		else 
		{
			driver.findElement(By.tagName("body")).sendKeys(Keys.CONTROL + "t");
		}
		driver.get(url);
	}

	/**
	 * Close Current Tab In Web Browser
	 * 
	 * @param driver
	 */
	public static void closeCurrentTab(WebDriver driver) 
	{
		if (System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) 
		{
			driver.findElement(By.tagName("body")).sendKeys(Keys.COMMAND + "w");
		} 
		else 
		{
			driver.findElement(By.tagName("body")).sendKeys(Keys.CONTROL + "w");
		}
	}

	/**
	 * Perform Mouse Hover on element
	 * 
	 * @param driver
	 * @param ele
	 */
	public static void mouseHover(WebDriver driver, WebElement ele) 
	{
		Actions action = new Actions(driver);
		action.moveToElement(ele).build().perform();
	}

	public static void FocusOnEle(WebDriver driver, WebElement ele) 
	{
		Actions action = new Actions(driver);
		action.moveToElement(ele).perform();
	}
	/**
	 * Perform Mouse Hover using java sript executer
	 * 
	 * @param driver
	 * @param ele
	 */
	public static void mouseHoverUsingJS(WebDriver driver, WebElement ele) 
	{
		String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover',true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
		((JavascriptExecutor) driver).executeScript(mouseOverScript, ele);
	}
	
	public static void jsType(WebDriver driver, WebElement element,String value)
	{
		String je="return arguments[0].value='"+value+"';";
		((JavascriptExecutor) driver).executeScript(je, element);
	}


	/**
	 * Go to URL.
	 * 
	 * @param driver
	 * @param url
	 */
	public static void goToUrl(WebDriver driver, String url) 
	{
		driver.get(url);
	}

	/**
	 * Go to previous page
	 * 
	 * @param driver
	 */
	public static void goToPreviuosPage(WebDriver driver) 
	{
		driver.navigate().back();
	}

	/**
	 * Highlight Element
	 * 
	 * @param driver
	 * @param element
	 */
	public static void highlightElement(WebDriver driver, WebElement element) 
	{
		((JavascriptExecutor) driver).executeScript("arguments[0].style.border = '3px solid yellow'", element);
		pause(2);
	}

	/**
	 * Stop page loading
	 * 
	 * @param driver
	 */
	public static void Log(String msg) 
	{
		//Reporter.log(msg + "</br>");
		test.log(Status.INFO,"&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"+" "+msg);
	}
	
	public static void stopPageLoading(WebDriver driver) 
	{
		driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
	}

	public static  boolean isElementPresent(WebDriver driver,By identifier) 
	{		
		int len = driver.findElements(identifier).size();		
		if (len == 0)		
			return false;		
		else		
			return true;		
	}
	public static String getBeforeAndAfterDateMonthWise(int i)
	{
		Calendar cur = Calendar.getInstance();
		NumberFormat f = new DecimalFormat("00");
		cur.add(Calendar.MONTH, i);		
		return f.format((cur.get(Calendar.MONTH))+1)+"/"+f.format(cur.get(Calendar.DATE))+"/"+cur.get(Calendar.YEAR);
	}
	public static String getBeforeAndAfterDateMonthWise(int i,int n)
	{
		Calendar cur = Calendar.getInstance();
		NumberFormat f = new DecimalFormat("00");
		cur.add(Calendar.MONTH, i);		
		return f.format((cur.get(Calendar.MONTH))+1)+"/"+f.format(n)+"/"+cur.get(Calendar.YEAR);
	}
	public static String[] getBeforeAndAfterMonthYear(int i,int motnh,int year)
	{
		Calendar cur = Calendar.getInstance();
		cur.set(year, motnh, 1);
		cur.add(Calendar.MONTH, i);	
		return new String[] {cur.getDisplayName(Calendar.MONTH,Calendar.LONG,Locale.getDefault()),String.valueOf(cur.get(Calendar.YEAR))};//+"/"+cur.get(Calendar.YEAR);
	}
	public static int getMonthNumber(String str)
	{
		return Month.valueOf(str.toUpperCase()).getValue();
	}
	public static String getBeforeAndAfetrDateYearWis(int i)
	{  
		Calendar cur = Calendar.getInstance();
		NumberFormat f = new DecimalFormat("00");
		cur.add(Calendar.YEAR, i);		
		return f.format( cur.get(Calendar.MONTH))+"/"+f.format(cur.get(Calendar.DATE))+"/"+cur.get(Calendar.YEAR);
	}
	
	public static String getAbsolutePathForUpload(String Doc)
	{
		return new File(Doc).getAbsolutePath();
	}
	public static long dateCalculation(String addStart,String addEnd)
	{
		String dateStart = addStart;
		String dateStop = addEnd;
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");			
		Date d1=null;
		Date d2=null;
		long diffDays = 0;
		try {
			 d1 = format.parse(dateStart);
			 d2 = format.parse(dateStop);
			long diff = d2.getTime() - d1.getTime();				
			diffDays = diff / (24 * 60 * 60 * 1000)+1;			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return diffDays;		
	}
	public static int numberOfWeekDay(String addStart,String addEnd,String DayName)
	{
		int add=0;
		try {
		long diffDays=dateCalculation(addStart,addEnd);
		
		Calendar cur = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		
			cur.setTime(format.parse(addStart));
		
		
		
		 LocalDate date1=LocalDate.of(2018,1,1);
	        for(int i=0;i<diffDays;i++){
	            if(DayName.equalsIgnoreCase(date1.getDayOfWeek().toString())){
	            	add++;
	            }
	                date1=date1.plusDays(1);
	        }
		} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	        return add;
	}
		
	 public static void SwitchtoTab(WebDriver driver,int tabNumber)
	 {
	  ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
	  driver.switchTo().window(tabs.get(tabNumber));
	 }
	 
}
