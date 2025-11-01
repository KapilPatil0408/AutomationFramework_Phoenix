package com.api.tests;

import static com.api.utils.DateTimeUtil.getTimeWithDays;
import static com.api.utils.SpecUtil.*;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.hamcrest.Matchers;
import org.testng.annotations.*;

import com.api.constant.*;
import com.api.record.model.*;
import com.api.utils.DateTimeUtil;
import com.github.javafaker.Faker;


public class CreateJobAPITest2 {
	
	CreateJobPayload createJobPayload;
	private static final String COUNTRY= "India";
	
	@BeforeMethod(description="Creating create job api request payload")
	public void setup() {
		Faker faker= new Faker(new Locale("en_IND"));
		
		String fName= faker.name().firstName();
		String lName= faker.name().lastName();
		String mobileNumber= faker.numerify("70########");
		String alternateMobileNumber= faker.numerify("70########");
		String customerEmailAddress= faker.internet().emailAddress();
		String altCustomerEmailAddress= faker.internet().emailAddress();
		
		Customer customer = new Customer(fName, lName, mobileNumber, alternateMobileNumber, customerEmailAddress, altCustomerEmailAddress);
	
		String flatNumber= faker.numerify("###");
		String apartmentName= faker.address().streetName();
		String streetName= faker.address().streetName();
		String landmark= faker.address().streetName();
		String area= faker.address().streetName();
		String pinCode= faker.numerify("#####");
		String country= faker.address().country();
		String state= faker.address().state();
		
		CustomerAddress customerAddress= new CustomerAddress(flatNumber, apartmentName, streetName, landmark, area, pinCode, COUNTRY, state);

		String dop= DateTimeUtil.getTimeWithDays(10);
		String imeiSerialNumber= faker.numerify("###############");
		String popUrl= faker.internet().url();
		
		CustomerProduct customerProduct= new CustomerProduct(dop, imeiSerialNumber, imeiSerialNumber, imeiSerialNumber, popUrl, 1, 1);
		
		String fakeRemark= faker.lorem().sentence(5);
		// want to generate number between 1 to 27;
		Random random= new Random();
		int problemId= random.nextInt(26)+1;
		
		Problems problems= new Problems(problemId, fakeRemark);
		List<Problems> problemList= new ArrayList<Problems>();
		problemList.add(problems);
		
		createJobPayload= new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct, problemList);

		
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
