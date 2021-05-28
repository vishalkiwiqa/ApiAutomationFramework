package com.MBS.CleanseMatchData;

import static io.restassured.RestAssured.given;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.*;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.MBS.Init.URL;
import com.MBS.Utility.TestData;
import io.restassured.http.ContentType;
import io.restassured.response.Response;



public class CleanseMatchTest extends URL{
	
	Response response;
	private static Logger log = LogManager.getLogger(LoginTest.class.getName());
	
	@Test
	@Parameters({"SrcRecordId", "CompanyName", "Address","City", "State","Country",
		"UserName","ValueToCheckFromJson"})
	public void createCleanseMatchData(String SrcRecordId, String CompanyName, String Address, String City, String State,
			 String Country, String UserName, String ValueToCheckFromJson) throws IOException {
		
		log.info("Starting Test Case : Cleanse Match Data");
		String subDomainIs= TestData.getValueFromConfig("config.properties", "SubDomain");
		
		String endPointURI = URL.getEndPointUriCleanseMatch();
		response = 
			      given()
		          .headers(
		              "Authorization",
		              "Bearer "+TestData.getValueFromConfig("config.properties", "Token"),
		              "Content-Type",
		              ContentType.JSON,
		              "Accept",
		              ContentType.JSON)
		          .queryParam("subDomain", subDomainIs)
		          .queryParam("SrcRecordId", SrcRecordId)
		          .queryParam("CompanyName", CompanyName)
		          .queryParam("Address", Address)
//		          .queryParam("Address2", Address2)
		          .queryParam("City", City)
		          .queryParam("State", State)
//		          .queryParam("PostalCode", PostalCode)
		          .queryParam("Country", Country)
//		          .queryParam("PhoneNbr", PhoneNbr)
//		          .queryParam("Tags", Tags)
//		          .queryParam("DnBDUNSNumber", DnBDUNSNumber)
		          .queryParam("UserName", UserName)
//		          .queryParam("InLanguage", InLanguage)
//		          .queryParam("MaxCandidateCount", MaxCandidateCount)
//		          .queryParam("InpDUNS", InpDUNS)
//		          .queryParam("RegistrationNumber", RegistrationNumber)
//		          .queryParam("RegistrationNumberType", RegistrationNumberType)
//		          .queryParam("Domain", Domain)
//		          .queryParam("Email", Email)
		          .when()
		          .get(endPointURI)
		          .then()
		          .extract()
		          .response();
		log.info(response);
		
		String strResponse = response.getBody().asString();
		System.out.println("************RESPONSE JSON*************");
		System.out.println();
		System.out.println(strResponse);
		
		
		JSONObject jsonObject = new JSONObject(strResponse);
	    JSONObject myResponse = jsonObject.getJSONObject("Output");
	    JSONArray tsmresponse = (JSONArray) myResponse.get("MatchOutput");

	    ArrayList<String> list = new ArrayList<String>();

	    for(int i=0; i<tsmresponse.length(); i++){
	        list.add(tsmresponse.getJSONObject(i).getString(ValueToCheckFromJson));
	    }

	    System.out.println("DUNS Number Is "+list);
	
	}	
	
	public void verifySrcIdInMatchData() {
		
		int numOfFailure = 0;
		int step = 1;
		
		logStep(step++, "Click on Data Stewardship from Left side Menu");
		commonMethodsVerification = commonMethodsIndexPage.clickOnMenu("Data Stewardship");
		
		logStep(step++, "Click on Match Data from Data Stewardship Menu");
		commonMethodsVerification = commonMethodsIndexPage.clickOnMenu("Match Data");
		
		logStep(step++, "Click on File Filter button");
		dataStewardshipVerification = dataStewardshipIndexPage.clickOnFileFilter();
		
		logStep(step++, "Select value from file filter Radio Button.");
		dataStewardshipVerification = dataStewardshipIndexPage.selectValueFromFileFilter(OrderByColoum);
		log(" Selected value from file filter Radio Button is " +OrderByColoum);
		
		if(!SelectSrcId.equalsIgnoreCase(""))
		{
			
	    logStep(step++, "Click on Add Filter button");
		dataStewardshipVerification = dataStewardshipIndexPage.clickOnaddFilterBtn();	
			
		logStep(step++, "Select SrcID Value from Drop Down");
		dataStewardshipVerification = dataStewardshipIndexPage.clickOnDropDown(SelectSrcId);
		log("Value Selected from Drop Down is :"+SelectSrcId);
		
		logStep(step++, "Enter Value in Input");
		dataStewardshipVerification = dataStewardshipIndexPage.entervalueinInput(SrcIdValue);
		log("Value Entered in Input is :"+SrcIdValue);
		
		logStep(step++, "Click on Add Filter button");
		dataStewardshipVerification = dataStewardshipIndexPage.clickOnaddFilterBtn();
		
			log("Verify Src Id is Present in the Grid");
			if (dataStewardshipVerification.verifySrcID(SrcIdValue)) {

				logStatus(1, " Success Message displayed like " + SrcIdValue + " is shown in Grid .");
			} else {
				logStatus(2, " " + SrcIdValue + " name is not shown correct.");
				numOfFailure++;
			}
		
		}
	}
	

}
