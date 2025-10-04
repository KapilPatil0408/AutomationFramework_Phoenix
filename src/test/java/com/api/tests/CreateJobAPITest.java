package com.api.tests;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.record.model.CreateJobPayload;
import com.api.record.model.Customer;
import com.api.record.model.CustomerAddress;
import com.api.record.model.CustomerProduct;
import com.api.record.model.Problems;
import com.api.utils.SpecUtil;

import io.restassured.module.jsv.JsonSchemaValidator;

public class CreateJobAPITest {
	
	
	
	@Test
	public void createJobAPITest() {
		
		Customer customer= new Customer("Kapil", "Patil", "7028582296", "", "kapil9660@gmail.com", "");
		CustomerAddress customerAddress= new CustomerAddress("K 502", "Galaxy app", "Balaji nagar", "Tarabai park", "Kolhapur", "416112", "Maharashtra", "India");
		CustomerProduct customerProduct= new CustomerProduct("2025-05-12T18:30:00.000Z", "499863806376052", "499863806376052", "499863806376052", "2025-05-12T18:30:00.000Z", 1, 1);
		Problems problem= new Problems(1, "Battery issue");
		List<Problems> problemList= new ArrayList<Problems>();
		problemList.add(problem);
		CreateJobPayload createJobPayload= new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct, problemList);
		
		given()
		.spec(SpecUtil.requestSpecWithAuth(Role.FD, createJobPayload))
		.log().all()
		.when()
		.post("job/create")
		.then()
		.spec(SpecUtil.responseSpec_OK())
		.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("response-schema/createJobAPIResponseSchema.json"))
		.body("message", Matchers.equalTo("Job created successfully. "))
		.body("data.mst_service_location_id", Matchers.equalTo(1))
		.body("data.job_number", Matchers.startsWith("JOB_"));
			
	}

}
