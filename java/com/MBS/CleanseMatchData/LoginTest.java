package com.MBS.CleanseMatchData;

import java.io.IOException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.MBS.Init.URL;
import com.MBS.Utility.TestData;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class LoginTest extends URL{
	
	Response response;
	private static Logger log = LogManager.getLogger(LoginTest.class.getName());
	
	@Test
	@Parameters({"subDomain"})
	public void doLogin(String subDomain) throws IOException {
		
		log.info("Starting Test Case : doLogin");
	//	String loginPayLoad = PayLoadConvertor.generatePayLoadString("configProperties.json"); 
		
		String endPointURI = URL.getEndPoint();
		response = 
			      given()
		          .headers(
		              "Authorization",
		              "Basic aTZVYnNOSEtoVUF5SVAwWTBQeWFXZz09OnQ2bUNobHYzR1RlVHJ5eFk0czhaUWc9PQ==",
		              "Content-Type",
		              ContentType.JSON,
		              "Accept",
		              ContentType.JSON).queryParam("subDomain", subDomain)
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
		
		JsonPath jsonRes = new JsonPath(strResponse);
		String sessionTD = jsonRes.getString("session.value");
		log.info("Session ID : "+sessionTD);
		
		JsonPath jsonPath = response.jsonPath();
		String accessToken =jsonPath.get("access_token");
		TestData.setValueConfig("config.properties", "Token", accessToken);
		
		System.out.println(" Access Token Is : "+accessToken);
	}
	

}
