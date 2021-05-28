package com.MBS.ExtractJson;

import io.restassured.response.Response;
import io.restassured.path.json.JsonPath;

public class ExtractJsonValue {
	
	static Response response;
	
	public static void extractAccessToken(String value) {
		
		
		JsonPath jsonPath = response.jsonPath();
		String accessToken =jsonPath.get(value);

		
		System.out.println("Access Token is : "+accessToken);
		
		 
	}

}
  