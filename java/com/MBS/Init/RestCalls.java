package com.MBS.Init;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestCalls {

	/*
	 * This Class will be responsible to fire Request
	 */

	private static Logger log = LogManager.getLogger(RestCalls.class.getName());

	public static Response GETRequest(String uRI) {

		log.info("Inside GET Request Call");
		RequestSpecification requestspecification = RestAssured.given();
		requestspecification.contentType(ContentType.JSON);
		Response response = requestspecification.get(uRI);
		log.debug(requestspecification.log().all());
		return response;
	}
	
	
//	public static Response GETRequest(String uRI, String strJSON) {
//
//		log.info("Inside GET Request Call");
//		RequestSpecification requestspecification = RestAssured.given().body(strJSON);
//		requestspecification.contentType(ContentType.JSON);
//		Response response = requestspecification.get(uRI);
//		log.debug(requestspecification.log().all());
//		return response;
//	}

	public static Response POSTRequest(String uRI, String strJSON) {

		log.info("Inside POSTRequest Call");
		RequestSpecification requestspecification = RestAssured.given().body(strJSON);
		requestspecification.contentType(ContentType.JSON);
		Response response = requestspecification.post(uRI);
		log.debug(requestspecification.log().all());
		return response;
	}

	public static Response PUTRequest(String uRI, String strJSON) {

		log.info("Inside PUTRequest Call");
		RequestSpecification requestspecification = RestAssured.given().body(strJSON);
		requestspecification.contentType(ContentType.JSON);
		Response response = requestspecification.put(uRI);
		log.debug(requestspecification.log().all());
		return response;
	}

	public static Response DELETERequest(String uRI, String strJSON) {

		log.info("Inside DELETERequest Call");
		RequestSpecification requestspecification = RestAssured.given().body(strJSON);
		requestspecification.contentType(ContentType.JSON);
		Response response = requestspecification.delete(uRI);
		log.debug(requestspecification.log().all());
		return response;
	}

}
