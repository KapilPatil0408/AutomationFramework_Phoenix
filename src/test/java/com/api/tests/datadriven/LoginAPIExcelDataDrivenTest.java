package com.api.tests.datadriven;

import static com.api.utils.SpecUtil.requestSpec;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import com.api.record.model.UserCredentials;

public class LoginAPIExcelDataDrivenTest {

	@Test(description = "Verify if login api working for FD user", groups = { "api", "regression","datadrivern" },
			dataProviderClass = com.dataproviders.DataProviderUtils.class, 
			dataProvider = "loginAPIExcelDataProvider")
	
	public void loginTest(UserCredentials userCredentails) {

		given().spec(requestSpec(userCredentails)).when().post("login").then().spec(responseSpec_OK()).and()
				.body("message", equalTo("Success")).and()
				.body(matchesJsonSchemaInClasspath("response-schema/loginResponseSchema.json"));

	}
}