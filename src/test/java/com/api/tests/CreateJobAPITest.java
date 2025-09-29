package com.api.tests;

import static io.restassured.RestAssured.*;

import org.testng.annotations.Test;

import com.api.constant.Role;
import com.api.pojo.CreateJobPayload;
import com.api.pojo.Customer;
import com.api.pojo.CustomerAddress;
import com.api.pojo.CustomerProduct;
import com.api.pojo.Problems;
import com.api.utils.SpecUtil;

public class CreateJobAPITest {
	
	
	
	@Test
	public void createJobAPITest() {
		
		Customer customer= new Customer("Kapil", "Patil", "7028582296", "", "kapil9660@gmail.com", "");
		CustomerAddress customerAddress= new CustomerAddress("K 502", "Galaxy app", "Balaji nagar", "Tarabai park", "Kolhapur", "416112", "Maharashtra", "India");
		CustomerProduct customerProduct= new CustomerProduct("2025-05-12T18:30:00.000Z", "402863806376052", "402863806376052", "402863806376052", "2025-05-12T18:30:00.000Z", 1, 1);
		Problems problem= new Problems(1, "Battery issue");
		Problems[] problemArray= new Problems[1];
		problemArray[0]= problem;
		CreateJobPayload createJobPayload= new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct, problemArray);
		
		given()
		.spec(SpecUtil.requestSpecWithAuth(Role.FD, createJobPayload))
		.log().all()
		.when()
		.post("job/create")
		.then()
		.spec(SpecUtil.responseSpec_OK());
			
	}

}
