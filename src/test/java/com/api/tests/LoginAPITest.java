package com.api.tests;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import com.api.pojo.UserCredentials;
import com.api.utils.SpecUtil;

import io.restassured.module.jsv.JsonSchemaValidator;

public class LoginAPITest {
	
	@Test
	public void loginTest() {
		
		UserCredentials userCrendentials= new UserCredentials("iamfd", "password");
		
		given()
		.spec(SpecUtil.requestSpec(userCrendentials))
		.when()
		.post("login")
		.then()
		.spec(SpecUtil.responseSpec_OK())
		.and()
		.body("message", equalTo("Success"))
		.and()
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/loginResponseSchema.json"));

}
}