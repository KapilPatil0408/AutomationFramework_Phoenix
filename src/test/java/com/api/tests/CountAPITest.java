package com.api.tests;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.utils.AuthTokenProvider;
import com.api.utils.ConfigManager;

import io.restassured.http.ContentType;

public class CountAPITest {
	
	
	@Test
	public void verifyCountAPIResponse() {
		
		given()
		.baseUri(ConfigManager.getProperty("BASE_URI"))
		.and()
		.accept(ContentType.JSON)
		.header("Authorization", AuthTokenProvider.getToken(Role.FD))
		.log().method()
		.log().uri()
		.log().body()
		.log().headers()
		.when()
		.get("/dashboard/count")
		.then()
		.log().all()
		.statusCode(200)
		.time(lessThan(1500L))
		.body("message", equalTo("Success"))
		.body("data", notNullValue())
		.body("data.size()", equalTo(3))
		.body("data.count", everyItem(greaterThanOrEqualTo(0)))
		.body("data.label", everyItem(not(blankOrNullString())))
		.body("data.key", containsInAnyOrder("pending_fst_assignment","created_today","pending_for_delivery"))
		.body(matchesJsonSchemaInClasspath("response-schema/countAPIResponseSchema.json"));
	}
	
	@Test
	public void verifyCountAPI_MissingAuthToken() {
		
		given()
		.baseUri(ConfigManager.getProperty("BASE_URI"))
		.and()
		.accept(ContentType.JSON)
		.log().method()
		.log().uri()
		.log().body()
		.log().headers()
		.when()
		.get("/dashboard/count")
		.then()
		.log().all()
		.statusCode(401);
	}

}
