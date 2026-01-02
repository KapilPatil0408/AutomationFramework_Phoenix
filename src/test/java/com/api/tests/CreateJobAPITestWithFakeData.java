package com.api.tests;

import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.record.model.CreateJobPayload;
import com.api.record.model.Customer;
import com.api.utils.FakerDataGenerator;
import com.database.dao.CustomerAddressDao;
import com.database.dao.CustomerDao;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;


public class CreateJobAPITestWithFakeData {
	
	private CreateJobPayload createJobPayload;
	
	@BeforeMethod(description="Creating create job api request payload")
	public void setup() {
		
		
		createJobPayload= FakerDataGenerator.generateFakeCreateJobData();

		
	}
	
	
	@Test(description="Verify if create job api response is able to create Inwarranty job", groups= {"api", "smoke", "regression"})
	public void createJobAPITest() {
		
		
	int customerId =	given()
		.spec(requestSpecWithAuth(Role.FD, createJobPayload))
		.log().all()
		.when()
		.post("job/create")
		.then()
		.spec(responseSpec_OK())
		.body(matchesJsonSchemaInClasspath("response-schema/createJobAPIResponseSchema.json"))
		.body("message", Matchers.equalTo("Job created successfully. "))
		.body("data.mst_service_location_id", Matchers.equalTo(1))
		.body("data.job_number", Matchers.startsWith("JOB_"))
		.extract().body().jsonPath().getInt("data.tr_customer_id");
	
		Customer expectedCustomerData = createJobPayload.customer();
		CustomerDBModel actualDataInDB = CustomerDao.getCustomerInfo(customerId);
		
		Assert.assertEquals(expectedCustomerData.first_name(), actualDataInDB.getFirst_name());
		Assert.assertEquals(expectedCustomerData.last_name(), actualDataInDB.getLast_name());
		Assert.assertEquals(expectedCustomerData.mobile_number(), actualDataInDB.getMobile_number());
		Assert.assertEquals(expectedCustomerData.mobile_number_alt(), actualDataInDB.getMobile_number_alt());
		Assert.assertEquals(expectedCustomerData.email_id(), actualDataInDB.getEmail_id());
		Assert.assertEquals(expectedCustomerData.email_id_alt(), actualDataInDB.getEmail_id_alt());
			
		
		CustomerAddressDBModel customerAddressDB=  CustomerAddressDao.getCustomerAddressData(actualDataInDB.getTr_customer_address_id());

		Assert.assertEquals(customerAddressDB.getFlat_number(), createJobPayload.customer_address().flat_number());
		Assert.assertEquals(customerAddressDB.getApartment_name(), createJobPayload.customer_address().apartment_name());
		Assert.assertEquals(customerAddressDB.getArea(), createJobPayload.customer_address().area());
		Assert.assertEquals(customerAddressDB.getLandmark(), createJobPayload.customer_address().landmark());
		
		Assert.assertEquals(customerAddressDB.getState(), createJobPayload.customer_address().state());
		Assert.assertEquals(customerAddressDB.getStreet_name(), createJobPayload.customer_address().street_name());
		Assert.assertEquals(customerAddressDB.getCountry(), createJobPayload.customer_address().country());
		Assert.assertEquals(customerAddressDB.getPincode(), createJobPayload.customer_address().pincode());
	}

}
