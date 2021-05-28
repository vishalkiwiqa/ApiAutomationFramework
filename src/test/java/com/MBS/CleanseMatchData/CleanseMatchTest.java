package com.MBS.CleanseMatchData;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.record.CommonObjectDataSubRecord;
import org.json.*;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.MBS.Init.Common;
import com.MBS.Init.URL;
import com.MBS.Utility.TestData;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;



public class CleanseMatchTest extends URL{
	
	static Response response=null;
	static String strResponse =null;
	private static Logger log = LogManager.getLogger(LoginTest.class.getName());
	
	@Test
	@Parameters({"SrcRecordId", "CompanyName", "Address","Address2","City", "State","PostalCode","Country","PhoneNbr","Tags","DnBDUNSNumber","UserName","InLanguage","MaxCandidateCount","InpDUNS",
		"RegistrationNumber","RegistrationNumberType","Domain","Email","ValueToCheckFromJson"})
	public void createCleanseMatchData(String SrcRecordId, String CompanyName, String Address, String Address2,String City, String State, String PostalCode,String Country,String PhoneNbr,
			String Tags,String DnBDUNSNumber,String UserName,String InLanguage,String MaxCandidateCount,String InpDUNS,String RegistrationNumber,String RegistrationNumberType,
			String Domain,String Email,String ValueToCheckFromJson) throws IOException {
		
		log.info("Starting Test Case : Cleanse Match Data");
		
		String endPointURI ="";
		String srcIdIs="";
		String compnayName="";
		String address="";
		String address2="";
		String city="";
		String state="";
		String postalCode="";
		String country="";
		String phoneNumber="";
		String tags="";
		String dunsnumber = "";
		String username ="";
		String inlanguage ="";
		String maxCandidateCount ="";
		String inpDuns ="";
		String registrationNbr ="";
		String registrationType ="";
		String domain ="";
		String email ="";
		
		 endPointURI = URL.getEndPointUriCleanseMatchCall();
		
		 if(!SrcRecordId.equalsIgnoreCase("")) {
			 srcIdIs ="SrcRecordId"+"="+SrcRecordId.toString()+"&";
		 }
		 if(!CompanyName.equalsIgnoreCase("")) {
			 compnayName = "CompanyName"+"="+CompanyName.toString()+"&";
		 }
		 if(!Address.equalsIgnoreCase("")) {
			 address = "Address"+"="+Address.toString()+"&";
		 }
		 if(!Address2.equalsIgnoreCase("")) {
			 address2 = "Address2"+"="+Address2.toString()+"&";
		 }
		 
		 if(!City.equalsIgnoreCase("")) {
			 city = "City"+"="+City.toString()+"&";
		 }
		 if(!State.equalsIgnoreCase("")) {
			 state = "State"+"="+State.toString()+"&";
		 }
		 if(!PostalCode.equalsIgnoreCase("")) {
			 postalCode = "PostalCode"+"="+PostalCode.toString()+"&";
		 }
		 
		 if(!Country.equalsIgnoreCase("")) {
			 country = "Country"+"="+Country.toString()+"&";
		 }
		 if(!PhoneNbr.equalsIgnoreCase("")) {
			 phoneNumber = "PhoneNbr"+"="+PhoneNbr.toString()+"&";
		 }
		 if(!Tags.equalsIgnoreCase("")) {
			 tags = "Tags"+"="+Tags.toString()+"&";
		 }
		 
		 if(!DnBDUNSNumber.equalsIgnoreCase("")) {
			 dunsnumber = "DnBDUNSNumber"+"="+DnBDUNSNumber.toString()+"&";
		 }
		 if(!UserName.equalsIgnoreCase("")) {
			 username = "UserName"+"="+UserName.toString()+"&";
		 }
		 
		 if(!InLanguage.equalsIgnoreCase("")) {
			 inlanguage = "InLanguage"+"="+InLanguage.toString()+"&";
		 }
		 if(!MaxCandidateCount.equalsIgnoreCase("")) {
			 maxCandidateCount = "MaxCandidateCount"+"="+MaxCandidateCount.toString()+"&";
		 }
		 if(!InpDUNS.equalsIgnoreCase("")) {
			 inpDuns = "InpDUNS"+"="+InpDUNS.toString()+"&";
		 }
		 if(!RegistrationNumber.equalsIgnoreCase("")) {
			 registrationNbr = "RegistrationNumber"+"="+RegistrationNumber.toString()+"&";
		 }
		 if(!RegistrationNumberType.equalsIgnoreCase("")) {
			 srcIdIs = "RegistrationNumberType"+"="+RegistrationNumberType.toString()+"&";
		 }
		 if(!Domain.equalsIgnoreCase("")) {
			 domain = "Domain"+"="+Domain.toString()+"&";
		 }
		 if(!Email.equalsIgnoreCase("")) {
			 email = "Email"+"="+Email.toString();
		 }
		 
		String actualURIIs= endPointURI+srcIdIs+compnayName+address+address2+city+state+postalCode+country+phoneNumber+tags+dunsnumber+username+inlanguage+maxCandidateCount+
				inpDuns+registrationNbr+registrationType+domain+email;
		log("Actual URI Calling is : "+actualURIIs);
		 
		response = 
			      given()
		          .headers(
		              "Authorization",
		              "Bearer "+TestData.getValueFromConfig("config.properties", "Token"),
		              "Content-Type",
		              ContentType.JSON,
		              "Accept",
		              ContentType.JSON)
		          .when()
		          .get(actualURIIs)
		          .then()
		          .extract()
		          .response();

		log.info(response);
		
		strResponse = response.getBody().asString();
		System.out.println("************RESPONSE JSON*************");
		System.out.println();
		System.out.println(strResponse);
		
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode,200, "Correct status code returned");
		
		JsonPath jsonPath = response.jsonPath();
		String accessToken =jsonPath.get("ResultText");
		System.out.println(" Result Is : "+accessToken);

		JSONObject jsonObject = new JSONObject(strResponse);
	    JSONObject myResponse = jsonObject.getJSONObject("Output");
	    JSONArray tsmresponse = (JSONArray) myResponse.get("MatchOutput");
	    
	    ArrayList<String> list = new ArrayList<String>();

	    for(int i=0; i<tsmresponse.length(); i++){
	        list.add(tsmresponse.getJSONObject(i).getString(ValueToCheckFromJson));
	    }

	    log("Data Shown Is :" +list);
	
		
	}
	
	public static ArrayList<String> getResponseValue(String ValueToCheckFromJson) {
		
		JSONObject jsonObject = new JSONObject(strResponse);
	    JSONObject myResponse = jsonObject.getJSONObject("Output");
	    JSONArray tsmresponse = (JSONArray) myResponse.get("MatchOutput");
	    
	    ArrayList<String> list = new ArrayList<String>();

	    for(int i=0; i<tsmresponse.length(); i++){
	        list.add(tsmresponse.getJSONObject(i).getString(ValueToCheckFromJson));
	    }
	    log("Data Shown Is :" +list);
	    
		return list;
	}
	
	public static ArrayList<String> getResponseValueDunsNumber() {
		
		JSONObject jsonObject = new JSONObject(strResponse);
	    JSONObject myResponse = jsonObject.getJSONObject("Output");
	    JSONArray tsmresponse = (JSONArray) myResponse.get("MatchOutput");
	    
	    ArrayList<String> list = new ArrayList<String>();

	    for(int i=0; i<tsmresponse.length(); i++){
	        list.add(tsmresponse.getJSONObject(i).getString("DnBDUNSNumber"));
	    }
	    log("Data Shown Is :" +list);
	    
		return list;
	}
	

	public static String acceptMatchCandidateCall(String UserName,String SrcRecordId,String Tags,String DnBDUNSNumber) throws IOException {
		
		String accessToken="";
		String strResponseValue="";
		log.info("Starting Test Case : Accept Match Candidate Call");
		
		ArrayList<String> valueIs = CleanseMatchTest.getResponseValue("DnBDUNSNumber");
		String resultType= CleanseMatchTest.getMatchedResult();
		
		String dunsNumberIs= valueIs.get(0);
		log("First Value of DunsNumber Is :"+dunsNumberIs);
		
		String endPointURI ="";
		String username ="";
		String srcIdIs="";
		String tags="";
		String dunsnumber ="";

		endPointURI = URL.getEndPointUriCleanseMatchCall();
		
		if(!UserName.equalsIgnoreCase("")) {
			 username = "UserName"+"="+UserName.toString()+"&";
		 }
		 if(!SrcRecordId.equalsIgnoreCase("")) {
			 srcIdIs ="SrcRecordId"+"="+SrcRecordId.toString()+"&";
		 }
		 if(!Tags.equalsIgnoreCase("")) {
			 tags = "Tags"+"="+Tags.toString()+"&";
		 }
		 if(!DnBDUNSNumber.equalsIgnoreCase("")) {
			 dunsnumber = "DnBDUNSNumber"+"="+DnBDUNSNumber.toString()+"&";
		 }
		 
		 dunsnumber = "DnBDUNSNumber"+"="+dunsNumberIs.toString()+"&";
		 
		if(resultType.equalsIgnoreCase("Match Candidates"))
		{
		 String actualURIIs= endPointURI+username+srcIdIs+tags+dunsnumber;
		 log("Accept Match Candidate Call URI Calling is  : "+actualURIIs);
		 
		 response = 
			      given()
		          .headers(
		              "Authorization",
		              "Bearer "+TestData.getValueFromConfig("config.properties", "Token"),
		              "Content-Type",
		              ContentType.JSON,
		              "Accept",
		              ContentType.JSON)
		          .when()
		          .get(actualURIIs)
		          .then()
		          .extract()
		          .response();

		log.info(response);
		
		strResponseValue = response.getBody().asString();
		System.out.println("************RESPONSE JSON*************");
		System.out.println();
		log(strResponse);
		
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode,200, "Correct status code returned");
		
		JsonPath jsonPath = response.jsonPath();
		accessToken =jsonPath.get("ResultText");
		log(" Result Is : "+accessToken);
		
		}
		else {
			log(" Result is not Match Result");
		}
		
		return accessToken;
	}
	
	public static String acceptCleansMatchEnrichment(String SrcRecordId,String Tags,String DnBDUNSNumber) throws IOException {
			
			String accessToken="";
			log.info("Starting Test Case : Accept Match Candidate Call");
			
			ArrayList<String> valueIs = CleanseMatchTest.getResponseValue("DnBDUNSNumber");
			String resultType= CleanseMatchTest.getMatchedResult();
			String dunsNumberIs= valueIs.get(0);
			log("First Value of DunsNumber Is :"+dunsNumberIs);
			
			String endPointURI ="";
			String srcIdIs="";
			String tags="";
			String dunsnumber ="";
	
			endPointURI = URL.getEndPointUriCleansMatchEnrichmentCall();
			
			 if(!SrcRecordId.equalsIgnoreCase("")) {
				 srcIdIs ="SrcRecordId"+"="+SrcRecordId.toString()+"&";
			 }
			 if(!Tags.equalsIgnoreCase("")) {
				 tags = "Tags"+"="+Tags.toString()+"&";
			 }
			 if(!DnBDUNSNumber.equalsIgnoreCase("")) {
				 dunsnumber = "DnBDUNSNumber"+"="+DnBDUNSNumber.toString()+"&";
			 }

			 
			if(resultType.equalsIgnoreCase("Matched"))
			{
			 String actualURIIs= endPointURI+srcIdIs+tags+dunsnumber;
			 log("Accept Match Candidate Call URI Calling is  : "+actualURIIs);
			 
			 response = 
				      given()
			          .headers(
			              "Authorization",
			              "Bearer "+TestData.getValueFromConfig("config.properties", "Token"),
			              "Content-Type",
			              ContentType.JSON,
			              "Accept",
			              ContentType.JSON)
			          .when()
			          .get(actualURIIs)
			          .then()
			          .extract()
			          .response();
	
			log.info(response);
			
			strResponse = response.getBody().asString();
			System.out.println("************RESPONSE JSON*************");
			System.out.println();
			log(strResponse);
			
			int statusCode = response.getStatusCode();
			Assert.assertEquals(statusCode,200, "Correct status code returned");
			
			}
			else {
				log(" Result is not Match Result");
			}
			
			return accessToken;
		}
	
	
	public static String refreshDunsNumberIs(String Country,String Tags,String DnBDUNSNumber) throws IOException {
		
		log.info("Starting Test Case : Accept Match Candidate Call");
		
		String endPointURI ="";
		String country="";
		String tags="";
		String dunsnumber ="";

		endPointURI = URL.getEndPointUriRefreshDunsNumber();
		
		 if(!Country.equalsIgnoreCase("")) {
			 country ="Country"+"="+Country.toString()+"&";
		 }
		 if(!Tags.equalsIgnoreCase("")) {
			 tags = "Tags"+"="+Tags.toString()+"&";
		 }
		 if(!DnBDUNSNumber.equalsIgnoreCase("")) {
			 dunsnumber = "DUNSNumber"+"="+DnBDUNSNumber.toString()+"&";
		 }

		 endPointURI= endPointURI+country+dunsnumber+tags;
		 log("API URI Calling Is  : "+endPointURI);
		 
		 response = 
			      given()
		          .headers(
		              "Authorization",
		              "Bearer "+TestData.getValueFromConfig("config.properties", "Token"),
		              "Content-Type",
		              ContentType.JSON,
		              "Accept",
		              ContentType.JSON)
		          .when()
		          .get(endPointURI)
		          .then()
		          .extract()
		          .response();

		log.info(response);
		
		strResponse = response.getBody().asString();
		System.out.println("************RESPONSE JSON*************");
		System.out.println();
		log(strResponse);
		
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode,200, "Correct status code returned");
		log("Status Code show is "+statusCode);
		
		return strResponse;
	}
	
	
		public static String getMatchedResult() {
			JsonPath jsonPath = response.jsonPath();
			String accessToken =jsonPath.get("ResultText");
			System.out.println(" Result Is : "+accessToken);
			
			return accessToken;
		}
	

		public static String uploadCall(String filename) throws Exception {
		
			String endPointURI ="";
			
			String jsonFileUploadedIs = TestData.readFromJsonFile("UploadData\\"+filename);

			endPointURI = URL.postUploadData();

			 log("API URI Calling Is  : "+endPointURI);
			 
			 response = 
				      given()
			          .headers(
			              "Authorization",
			              "Bearer "+TestData.getValueFromConfig("config.properties", "Token"),
			              "Content-Type",
			              ContentType.JSON,
			              "Accept",
			              ContentType.JSON)
			          .body(jsonFileUploadedIs)
			          .when()
			          .post(endPointURI)
			          .then()
			          .extract()
			          .response();

			log.info(response);
			
			strResponse = response.getBody().asString();
			System.out.println("************RESPONSE JSON*************");
			System.out.println();
			log(strResponse);
			
			return strResponse;
		}
	
		
}






































































































//.queryParam("SrcRecordId", SrcRecordId)
//.queryParam("CompanyName", CompanyName)
//.queryParam("Address", Address)
//.queryParam("Address2", Address2)
//.queryParam("City", City)
//.queryParam("State", State)
//.queryParam("PostalCode", PostalCode)
//.queryParam("Country", Country)
//.queryParam("PhoneNbr", PhoneNbr)
//.queryParam("Tags", Tags)
//.queryParam("DnBDUNSNumber", DnBDUNSNumber)
//.queryParam("UserName", UserName)
//.queryParam("InLanguage", InLanguage)
//.queryParam("MaxCandidateCount", MaxCandidateCount)
//.queryParam("InpDUNS", InpDUNS)
//.queryParam("RegistrationNumber", RegistrationNumber)
//.queryParam("RegistrationNumberType", RegistrationNumberType)
//.queryParam("Domain", Domain)
//.queryParam("Email", Email)
