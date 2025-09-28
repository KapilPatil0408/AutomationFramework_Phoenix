package com.api.tests;

import static com.api.constant.Role.*;
import static io.restassured.RestAssured.*;

import org.testng.annotations.Test;

import com.api.utils.SpecUtil;

import io.restassured.module.jsv.JsonSchemaValidator;

public class UserDetailsAPITest {

	@Test
	public void userDetailsAPIRequest() {
		
		given()
		.spec(SpecUtil.requestSpecWithAuth(FD))
		.when()
			.get("userdetails")
		.then()
		.spec(SpecUtil.responseSpec_OK())
		.and()
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/userDetailsResponseSchema.json"));
	}

}
