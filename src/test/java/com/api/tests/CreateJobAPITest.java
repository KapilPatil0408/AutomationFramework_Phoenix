package com.api.tests;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import com.api.constant.Model;
import com.api.constant.OEM;
import com.api.constant.Platform;
import com.api.constant.Problem;
import com.api.constant.Product;
import com.api.constant.Role;
import com.api.constant.ServiceLocation;
import com.api.constant.Warranty_Status;
import com.api.record.model.*;
import static  com.api.utils.DateTimeUtil.*;
import com.api.utils.SpecUtil;

import io.restassured.module.jsv.JsonSchemaValidator;

public class CreateJobAPITest {
	
	
	
	@Test
	public void createJobAPITest() {
		
		Customer customer= new Customer("Kapil", "Patil", "7028582296", "", "kapil9660@gmail.com", "");
		CustomerAddress customerAddress= new CustomerAddress("K 502", "Galaxy app", "Balaji nagar", "Tarabai park", "Kolhapur", "416112", "Maharashtra", "India");
		CustomerProduct customerProduct= new CustomerProduct(getTimeWithDays(10), "499863806376055", "499863806376055", "499863806376055", getTimeWithDays(10), Product.NEXUS_2.getCode(), Model.NEXUS_2_BLUE.getCode());
		Problems problem= new Problems(Problem.SMARTPHONE_IS_RUNNING_SLOW.getCode(), "Battery issue");
		List<Problems> problemList= new ArrayList<Problems>();
		problemList.add(problem);
		CreateJobPayload createJobPayload= new CreateJobPayload(ServiceLocation.SERVICE_LACTION_A.getCode(), Platform.FRONT_DESK.getCode(),
						Warranty_Status.IN_WARRANTY.getCode(), OEM.GOOGLE.getCode(), customer, customerAddress, customerProduct, problemList);
		
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
