package com.api.tests;

import static com.api.constant.Role.*;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.hamcrest.Matchers.*;


import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.*;

public class MasterAPITest {
	
	@Test(description="Verify the Master API response is shown correctly", groups= {"api", "smoke", "regression"})
	public void masterAPITest() {
		
		given()
		.spec(requestSpecWithAuth(FD))
		.when()
		.post("master")
		.then()
		.spec(responseSpec_OK())
		.body("message", equalTo("Success"))
		.body("data", notNullValue())
		.body("data", hasKey("mst_oem"))
		.body("data", hasKey("mst_model"))
		.body("$", hasKey("message"))
		.body("$", hasKey("data"))
		.body("data.mst_oem.size()", equalTo(2))  // check size pf JSON Array with Matchers
		.body("data.mst_model.size()", greaterThan(0))
		.body("data.mst_oem.id", everyItem(notNullValue()))
		.body("data.mst_oem.name", everyItem(notNullValue()))
		.body(matchesJsonSchemaInClasspath("response-schema/masterAPIResponseSchema.json"));
	}
	
	@Test(description="Verify the Master correct status code for invalid token", groups= {"api", "negative", "smoke", "regression"})
	public void invalidTokenMasterAPITest() {
		given()
		.spec(requestSpec())
		.when()
		.post("master")
		.then()
		.spec(responseSpec_TEXT(401));
	}

}
