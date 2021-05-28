package com.MBS.Login;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.MBS.Init.Common;
import com.MBS.Init.SeleniumInit;
import com.MBS.Utility.TestData;
import com.beust.jcommander.Parameter;

public class LoginIndex extends SeleniumInit{
	
	@Parameters({ "loginid", "password","username","SequrityAns" })
	@Test
	public void loginandlogout(String loginId, String password,String username,String SequrityAns) throws IOException{
		
		int numOfFailure=0;
		int step=1;
		String emailuserone = TestData.getValueFromConfig("config.properties", "Email");
		String password1 = TestData.getValueFromConfig("config.properties", "Password");
		String securityAnswer=TestData.getValueFromConfig("config.properties", "SecurityAnswer");
		String user_name=username;//TestData.getCellValue("Data/MBSData.xlsx","Login",2,3);
		String dashboardusername=TestData.getValueFromConfig("config.properties", "DashboardUserName");
		
		 
		logStep(step++,"Open URL : "+testUrl);	
		
		/*if(commonMethodsVerification.verifysignin("Sign In"))
		{
			logStatus(1," Verify that user is able to redirect to Sign In Page.");
		}
		else{
			logStatus(2," User is not able to redirect to Sign In Page.");
			numOfFailure++;
		}*/
		
		/*logStep(step++,"Do login with valid email and password.");
		loginVerification=loginIndexPage.doLogin(emailuserone, password1);
		log("Entered Email: "+emailuserone);
		log("Entered Password: "+password1);
		
		logStep(step++,"Enter Security Answer and click on Submit");
		loginVerification=loginIndexPage.submitSecurityAnswer(securityAnswer);
		log("Entered Security Answer: "+securityAnswer);*/
		
		
		if(dashboardVerification.verifyLoggedInUserName(dashboardusername)) 
		{
			logStatus(1," Verify "+dashboardusername+" is redirected to Dashboard Page");
		}
		else{
			logStatus(2," User is not Redirected to Dashboard Page");
			numOfFailure++;
		}
		
		
		logStep(step++,"Mouser over to active user.");
		loginVerification=loginIndexPage.mouseovertoactiveuser();
		
		
		if(dashboardVerification.ActiveUser(dashboardusername))	
		{
			logStatus(1," Verify that "+dashboardusername+" is Active user");
		}
		else{
			logStatus(2," "+dashboardusername+" is not a active user.");
			numOfFailure++;
		}
		
		/*
		if(dashoardVerification.ActiveUser(user_name))
		{
			logStatus(2," Verify that "+user_name+" is not Active user. " );
			numOfFailure++;
		}
		
		else{
			logStatus(1," "+dashboardusername+" is a active user"); 
		}*/
		
		
		logStep(step++,"Click on Logout Icon.");
		loginVerification=loginIndexPage.logout();
		
		
		logStep(step++,"Click on Yes Button form Confirmation Pop Up");
		dashboardVerification=dashoardIndexPage.verifyYesandNoButton(" No");
		
		Common.Pause(3);
		
		if(dashboardVerification.verifyLoggedInUserName(dashboardusername))
		{
			logStatus(1," Verify "+dashboardusername+" is redirected to Dashboard Page. ");
		}
		else{
			logStatus(2," "+dashboardusername+" User has been logged out");
			numOfFailure++;
		}
		
		
		logStep(step++,"Click on Logout Icon.");
		loginVerification=loginIndexPage.logout();
		
		
		logStep(step++,"Click on Yes Button form Confirmation Pop Up");
		dashboardVerification=dashoardIndexPage.verifyYesandNoButton(" Yes");
		
		Common.pause(7);
		
		if(loginVerification.verifylogouturl("https://matchbookservices.com/blog/") || loginVerification.verifylogouturl("https://www.dnb.com/"))
		{
			logStatus(1," Verified that user Logged out successfully .");
		}
		else{
			logStatus(2," User is not able to Logout");
			numOfFailure++;
		}
		
			
		driver.navigate().to(testUrl);
		
		 
		logStep(step++,"Open URL : " +testUrl);	
		
		driver.navigate().refresh();
		
		if(commonMethodsVerification.verifysignin("Sign In"))
		{
			logStatus(1," User is able to redirect to Sign In Page.");
		}
		else{
			logStatus(2," User is not able to redirect to Sign In Page.");
			numOfFailure++;
		}
		
		String username1=loginId;//TestData.getCellValue("Data/MBSData.xlsx","Login",2,0);
		String pswrd=password;//TestData.getCellValue("Data/MBSData.xlsx","Login",2,1);
		String sequrityAns=SequrityAns;
		logStep(step++,"Do login with valid email and password with other user.");
		loginVerification=loginIndexPage.doLogin(username1, pswrd);
		log("Entered Email: "+username1);
		log("Entered Password: "+pswrd);
		
		String scrtyanswer=TestData.getCellValue("Data/MBSData.xlsx","Login",2,2);
		
		logStep(step++,"Enter Security Answer and click on Submit");
		loginVerification=loginIndexPage.submitSecurityAnswer(sequrityAns);
		log("Entered Security Answer: "+sequrityAns);
		
		
		if(dashboardVerification.verifyLoggedInUserName(user_name))
		{
			logStatus(1," Verify "+user_name+" is redirected to Dashboard Page ");
		}
		else{
			logStatus(2," "+user_name+" is unable to Redirect");
			numOfFailure++;
		}
		
		logStep(step++,"Mouser over to active user.");
		loginVerification=loginIndexPage.mouseovertoactiveuser();
		
		if(dashboardVerification.ActiveUser(user_name))
		{
			logStatus(1,"Verify that "+user_name+" is Active user. ");
		}
		else{
			logStatus(2," "+user_name+" is not an Active user. ");
			numOfFailure++;
		}
		
		
		if(dashboardVerification.ActiveUser(dashboardusername))
		{
			logStatus(2," Verify that "+dashboardusername+" is not active user");
			numOfFailure++;
		}
		else{
			logStatus(1," "+user_name+" is active user");
		}
		
		logStep(step++,"Go to Logout Icon.");
		loginVerification=loginIndexPage.logout();
		
		logStep(step++,"Click on Logout Button.");
		dashboardVerification=dashoardIndexPage.verifyYesandNoButton(" Yes");
		
		Common.pause(5);
		
		if(loginVerification.verifylogouturl("https://matchbookservices.com/blog/") || loginVerification.verifylogouturl("https://www.dnb.com/"))
		{
			logStatus(1," Verified that User Logged Out successfully and URL matched : 'https://matchbookservices.com/blog/'.");
		}
		else{
			logStatus(2," User is not able to Logout");
			numOfFailure++;
		}
		driver.get(testUrl);
		logStep(step++,"Do login with valid email and password.");
		loginVerification=loginIndexPage.doLogin(emailuserone, password1);
		log("Entered Email: "+emailuserone);
		log("Entered Password: "+password1);
		
		logStep(step++,"Enter Security Answer and click on Submit");
		loginVerification=loginIndexPage.submitSecurityAnswer(securityAnswer);
		log("Entered Security Answer: "+securityAnswer);
		
		if (numOfFailure > 0) {
			Assert.assertTrue(false);
		} 
		
	}
	
	@Test
	public void logout() throws IOException{
		
		int numOfFailure=0;
		int step=1;
		 
		logStep(step++,"Go to Logout Icon.");
		loginVerification=loginIndexPage.logout();
		
		Common.pause(2);
		logStep(step++,"Click on Logout Button.");
		dashboardVerification=dashoardIndexPage.verifyYesandNoButton(" Yes");
		
		Common.pause(5);
		
		if(loginVerification.verifylogouturl("https://matchbookservices.com/blog/") || loginVerification.verifylogouturl("https://www.dnb.com/"))
		{
			logStatus(1," Verified that User Logged Out successfully and URL matched : 'https://matchbookservices.com/blog/' or https://www.dnb.com/.");
		}
		else{
			logStatus(2," User is not able to Logout");
			numOfFailure++;
		}
		
		if (numOfFailure > 0) {
			Assert.assertTrue(false);
		} 
		
	}
	
	@Parameters({ "LoginEmail", "Password","SecurityAnswer","DashboardUserName" })
	@Test
	public void login(String LoginEmail, String Password,String SecurityAnswer,String DashboardUserName) throws IOException{
		
		int numOfFailure=0;
		int step=1;
		 
		logStep(step++,"Open URL : "+testUrl);	
		
		if(commonMethodsVerification.verifysignin("Sign In"))
		{
			logStatus(1," Verify that user is able to redirect to Sign In Page.");
		}
		else{
			logStatus(2," User is not able to redirect to Sign In Page.");
			numOfFailure++;
		}
		
		logStep(step++,"Do login with valid email and password.");
		loginVerification=loginIndexPage.doLogin(LoginEmail, Password);
		log("Entered Email: "+LoginEmail);
		log("Entered Password: "+Password);
		
		logStep(step++,"Enter Security Answer and click on Submit");
		loginVerification=loginIndexPage.submitSecurityAnswer(SecurityAnswer);
		log("Entered Security Answer: "+SecurityAnswer);
		
		
		if(dashboardVerification.verifyLoggedInUserName(DashboardUserName)) 
		{
			logStatus(1," Verify "+DashboardUserName+" is redirected to Dashboard Page");
		}
		else{
			logStatus(2," User is not Redirected to Dashboard Page");
			numOfFailure++;
		}
		
		if (numOfFailure > 0) {
			Assert.assertTrue(false);
		} 
		
	}


}
