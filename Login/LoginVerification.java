package com.MBS.Login;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.MBS.Init.AbstractPage;
import com.MBS.Init.Common;

public class LoginVerification extends AbstractPage {

	public LoginVerification(WebDriver driver) {
		super(driver);
		
	}
		
		
	/*
	 * @FindBy(xpath=".//button[text()=' Yes']") WebElement yesbutton;
	 * 
	 * public Boolean verifyYesButton(String value) {
	 * 
	 * Common.clickableElement(yesbutton, driver);
	 * if(yesbutton.getText().contains(value)) {
	 * System.out.println("User is able to click on Yes button :"
	 * +yesbutton.getText()); Common.log("User is able to click on Yes button  :"
	 * +yesbutton.getText()); return true; } else {
	 * System.out.println("User is not being able to click on Yes Button"); return
	 * false; } }
	 */
	
	
	
	public Boolean verifylogouturl(String value) {
		
		String actualTitle = driver.getCurrentUrl();
		String expectedTitle = value;
				//"https://matchbookservices.com/blog/";
		
		if(actualTitle.equalsIgnoreCase(expectedTitle)) {
			System.out.println("Verify that User Logged out succesfully.");
			return true;
		}
		else
			System.out.println("User did not logged out.");
			return false;
	}
}

