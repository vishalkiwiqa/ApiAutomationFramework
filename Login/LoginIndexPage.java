package com.MBS.Login;




import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.MBS.Init.Common;
import com.MBS.Init.AbstractPage;

public class LoginIndexPage extends AbstractPage{

	public LoginIndexPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	@FindBy(id="EmailAddress")
	WebElement emailID;
	@FindBy(id="Password")
	WebElement passwordTxt;
	@FindBy(xpath=".//button[@type='submit']")
	WebElement loginBtn;
	
	public LoginVerification doLogin(String email, String password)
	{
		Common.clickableElement(emailID, driver);
		//emailID.clear();
		emailID.sendKeys(email);
		
		Common.clickableElement(passwordTxt, driver);
		//passwordTxt.clear();
		passwordTxt.sendKeys(password);
		
		Common.clickableElement(loginBtn, driver);
		loginBtn.click();
		return new LoginVerification(driver);
	}
	
	@FindBy(id="SecurityAnswer")
	WebElement answerTxt;
	@FindBy(xpath=".//input[@type='submit']")
	WebElement submitBtn;
	public LoginVerification submitSecurityAnswer(String Ans)
	{
	
		Common.clickableElement(answerTxt, driver);
		//answerTxt.clear();
		answerTxt.sendKeys(Ans);
		
		Common.clickableElement(answerTxt, driver);
		submitBtn.click();
		return new LoginVerification(driver);
	}
	
	@FindBy(xpath=".//a[@data-action='userLogout']")
	WebElement logout;
	
	public LoginVerification logout()
	{
		Common.PresenceOfElement(By.id("logout"), driver);
		Common.clickableElement(logout, driver);
		Common.jsClick(driver, logout);
		
		return new LoginVerification(driver);
		}
	
	
	@FindBy(xpath=".//span[contains(.,'Active Users ')]")
	WebElement mouseovertoactiveuser;
	
	public LoginVerification mouseovertoactiveuser()
	{
		Common.mouseHover(driver, mouseovertoactiveuser);
		
		return new LoginVerification(driver);
	}
	
}
