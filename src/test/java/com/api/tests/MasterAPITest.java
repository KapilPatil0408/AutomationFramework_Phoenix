package com.api.tests;

import static com.api.constant.Role.FD;
import static com.api.utils.AuthTokenProvider.*;
import static com.api.utils.ConfigManager.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class MasterAPITest {
	
	@Test
	public void masterAPITest() {
		
		given()
		.baseUri(getProperty("BASE_URI"))
		.and()
		.contentType("")
		.header("Authorization", getToken(FD))
		.log().method()
		.log().uri()
		.log().body()
		.log().headers()
		.when()
		.post("master")
		.then()
		.log().all()
		.statusCode(200)
		.time(lessThan(1500L))
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
	
	@Test
	public void invalidTokenMasterAPITest() {
		
		given()
		.baseUri(getProperty("BASE_URI"))
		.and()
		.contentType("")
		.log().method()
		.log().uri()
		.log().body()
		.log().headers()
		.when()
		.post("master")
		.then()
		.log().all()
		.statusCode(401);
	}

}
