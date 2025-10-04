package com.api.tests;

import static com.api.utils.DateTimeUtil.getTimeWithDays;
import static com.api.utils.SpecUtil.*;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.testng.annotations.*;

import com.api.constant.*;
import com.api.record.model.*;


public class CreateJobAPITest {
	CreateJobPayload createJobPayload;
	
	@BeforeMethod(description="Creating create job api request payload")
	public void setup() {
		Customer customer= new Customer("Kapil", "Patil", "7028582296", "", "kapil9660@gmail.com", "");
		CustomerAddress customerAddress= new CustomerAddress("K 502", "Galaxy app", "Balaji nagar", "Tarabai park", "Kolhapur", "416112", "Maharashtra", "India");
		CustomerProduct customerProduct= new CustomerProduct(getTimeWithDays(10), "499863806376055", "499863806376055", "499863806376055", getTimeWithDays(10), Product.NEXUS_2.getCode(), Model.NEXUS_2_BLUE.getCode());
		Problems problem= new Problems(Problem.SMARTPHONE_IS_RUNNING_SLOW.getCode(), "Battery issue");
		List<Problems> problemList= new ArrayList<Problems>();
		problemList.add(problem);
		createJobPayload= new CreateJobPayload(ServiceLocation.SERVICE_LACTION_A.getCode(), Platform.FRONT_DESK.getCode(),
						Warranty_Status.IN_WARRANTY.getCode(), OEM.GOOGLE.getCode(), customer, customerAddress, customerProduct, problemList);
		
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
