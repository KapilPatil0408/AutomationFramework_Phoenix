package com.api.tests;

import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.record.model.CreateJobPayload;
import com.api.utils.FakerDataGenerator;


public class CreateJobAPITestWithFakeData {
	
	CreateJobPayload createJobPayload;
	
	@BeforeMethod(description="Creating create job api request payload")
	public void setup() {
		
		
		createJobPayload= FakerDataGenerator.generateFakeCreateJobData();

		
	}
	
	
	@Test(description="Verify if create job api response is able to create Inwarranty job", groups= {"api", "smoke", "regression"})
	public void createJobAPITest() {
		
		
		given()
		.spec(requestSpecWithAuth(Role.FD, createJobPayload))
		.log().all()
		.when()
		.post("job/create")
		.then()
		.spec(responseSpec_OK())
		.body(matchesJsonSchemaInClasspath("response-schema/createJobAPIResponseSchema.json"))
		.body("message", Matchers.equalTo("Job created successfully. "))
		.body("data.mst_service_location_id", Matchers.equalTo(1))
		.body("data.job_number", Matchers.startsWith("JOB_"));
			
	}

}
