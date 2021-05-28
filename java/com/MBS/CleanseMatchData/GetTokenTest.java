package com.MBS.CleanseMatchData;

import static io.restassured.RestAssured.given;
import java.io.IOException;

import org.testng.annotations.Test;

import com.MBS.Init.URL;
import com.MBS.Utility.TestData;
import io.restassured.response.Response;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;


public class GetTokenTest extends URL{
	
	public static Response response;
	
	@Test
	public static void generateToken() throws IOException {
		
		 String userName = TestData.getValueFromConfig("config.properties", "APIKey");
		 String password =TestData.getValueFromConfig("config.properties", "APISecret");
//		 String subDomainIs= TestData.getValueFromConfig("config.properties", "SubDomain");
		
		String endPointURI = URL.getEndPointUriGetTokenIs();
		
		response = 
			      given()
		          .auth()
		          .preemptive()
		          .basic(userName, password)
		          .when()
		          .get(endPointURI)
		          .then()
		          .extract()
		          .response();
		
//		response = 
//			      given()
//		          .headers(
//		              "Authorization",
//		              "Basic "+TestData.getValueFromConfig("config.properties", "APIKey"),
//		              "Content-Type",
//		              ContentType.JSON,
//		              "Accept",
//		              ContentType.JSON).queryParam("subDomain", "mb-qa1")
//		          .when()
//		          .get(endPointURI)
//		          .then()
//		          .extract()
//		          .response();

		
		String strResponse = response.getBody().asString();
		System.out.println("************RESPONSE JSON*************");
		System.out.println();
		System.out.println(strResponse);
		
		JsonPath jsonPath = response.jsonPath();
		String accessToken =jsonPath.get("access_token");
		System.out.println(" Token Generated Is : "+accessToken);
		TestData.setValueConfig("config.properties", "Token", accessToken);
		
	}

}
