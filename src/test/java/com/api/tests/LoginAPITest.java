package com.api.tests;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.UserCredentials;

import static com.api.utils.SpecUtil.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class LoginAPITest {
	
	private UserCredentials userCrendentials;
	
	@BeforeMethod(description="Create payload for the login API")
	public void setup() {
		userCrendentials	= new UserCredentials("iamfd", "password");
	}
	
	@Test(description="Verify if login api working for FD user", groups= {"api", "regression", "smoke"})
	public void loginTest() {
		
		
		
		given()
		.spec(requestSpec(userCrendentials))
		.when()
		.post("login")
		.then()
		.spec(responseSpec_OK())
		.and()
		.body("message", equalTo("Success"))
		.and()
		.body(matchesJsonSchemaInClasspath("response-schema/loginResponseSchema.json"));

}
}