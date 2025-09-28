package com.api.tests;

import static com.api.constant.Role.*;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import com.api.utils.SpecUtil;

public class CountAPITest {
	
	
	@Test
	public void verifyCountAPIResponse() {
		
		given()
		.spec(SpecUtil.requestSpecWithAuth(FD))
		.when()
		.get("/dashboard/count")
		.then()
		.spec(SpecUtil.responseSpec_OK())
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
		.spec(SpecUtil.requestSpec())
		.when()
		.get("/dashboard/count")
		.then()
		.spec(SpecUtil.responseSpec_TEXT(401));
	}

}
