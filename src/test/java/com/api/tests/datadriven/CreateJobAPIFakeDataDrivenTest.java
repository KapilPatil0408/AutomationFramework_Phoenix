package com.api.tests.datadriven;

import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.record.model.CreateJobPayload;


public class CreateJobAPIFakeDataDrivenTest {
	
	
	@Test(description="Verify if create job api response is able to create Inwarranty job", groups= {"api", "regression", "datadriven"},
				dataProviderClass = com.dataproviders.DataProviderUtils.class,
				dataProvider ="createJobAPIFakerDataProvider")
	public void createJobAPITest(CreateJobPayload createJobPayload) {
	
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
