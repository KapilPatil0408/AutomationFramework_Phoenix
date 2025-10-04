package com.api.tests;

import static com.api.constant.Role.*;
import static io.restassured.RestAssured.*;

import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class UserDetailsAPITest {

	@Test(description="Verify the Userdeatils API response is shown correctly", groups= {"api", "smoke", "regression"})
	public void userDetailsAPIRequest() {
		
		given()
		.spec(requestSpecWithAuth(FD))
		.when()
			.get("userdetails")
		.then()
		.spec(responseSpec_OK())
		.and()
			.body(matchesJsonSchemaInClasspath("response-schema/userDetailsResponseSchema.json"));
	}

}
