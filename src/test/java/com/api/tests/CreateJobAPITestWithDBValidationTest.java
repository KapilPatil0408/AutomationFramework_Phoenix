package com.api.tests;

import static com.api.utils.DateTimeUtil.getTimeWithDays;
import static com.api.utils.SpecUtil.requestSpecWithAuth;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constant.Model;
import com.api.constant.OEM;
import com.api.constant.Platform;
import com.api.constant.Problem;
import com.api.constant.Product;
import com.api.constant.Role;
import com.api.constant.ServiceLocation;
import com.api.constant.Warranty_Status;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.database.dao.CustomerAddressDao;
import com.database.dao.CustomerDao;
import com.database.dao.CustomerProductDao;
import com.database.dao.JobHeadDao;
import com.database.dao.MapJobProblemDao;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import com.database.model.CustomerProductDBModel;
import com.database.model.JobHeadDBModel;
import com.database.model.MapJobProblemModel;

import io.restassured.response.Response;

public class CreateJobAPITestWithDBValidationTest {
	private CreateJobPayload createJobPayload;
	private Customer customer;
	private CustomerAddress customerAddress;
	private CustomerProduct customerProduct;

	@BeforeMethod(description = "Creating create job api request payload")
	public void setup() {
		customer = new Customer("Kapil", "Patil", "7028582296", "", "kapil9660@gmail.com", "");
		customerAddress = new CustomerAddress("K 502", "Galaxy app", "Balaji nagar", "Tarabai park",
				"Kolhapur", "416112", "Maharashtra", "India");
		customerProduct = new CustomerProduct(getTimeWithDays(10), "499863806375555", "499863806375555",
				"499863806375555", getTimeWithDays(10), Product.NEXUS_2.getCode(), Model.NEXUS_2_BLUE.getCode());
		Problems problem = new Problems(Problem.SMARTPHONE_IS_RUNNING_SLOW.getCode(), "Battery issue");
		List<Problems> problemList = new ArrayList<Problems>();
		problemList.add(problem);
		createJobPayload = new CreateJobPayload(ServiceLocation.SERVICE_LACTION_A.getCode(),
				Platform.FRONT_DESK.getCode(), Warranty_Status.IN_WARRANTY.getCode(), OEM.GOOGLE.getCode(), customer,
				customerAddress, customerProduct, problemList);

	}

	@Test(description = "Verify if create job api response is able to create Inwarranty job", groups = { "api", "smoke",
			"regression" })
	public void createJobAPITest() {

		Response response = given().spec(requestSpecWithAuth(Role.FD, createJobPayload)).log().all().when()
				.post("job/create").then().spec(responseSpec_OK())
				.body(matchesJsonSchemaInClasspath("response-schema/createJobAPIResponseSchema.json"))
				.body("message", Matchers.equalTo("Job created successfully. "))
				.body("data.mst_service_location_id", Matchers.equalTo(1))
				.body("data.job_number", Matchers.startsWith("JOB_"))
				.extract().response();
		System.out.println("-----------------------------------------");
	
		int customerId = response.then().extract().body().jsonPath().getInt("data.tr_customer_id");
		
		CustomerDBModel CustomerDataFromDB = CustomerDao.getCustomerInfo(customerId);
		System.out.println(CustomerDataFromDB);

		Assert.assertEquals(customer.first_name(), CustomerDataFromDB.getFirst_name());
		Assert.assertEquals(customer.last_name(), CustomerDataFromDB.getLast_name());
		Assert.assertEquals(customer.mobile_number(), CustomerDataFromDB.getMobile_number());
		Assert.assertEquals(customer.mobile_number_alt(), CustomerDataFromDB.getMobile_number_alt());
		Assert.assertEquals(customer.email_id(), CustomerDataFromDB.getEmail_id());
		Assert.assertEquals(customer.email_id_alt(), CustomerDataFromDB.getEmail_id_alt());
		
		
		CustomerAddressDBModel customerAddressDBModel=  CustomerAddressDao.getCustomerAddressData(CustomerDataFromDB.getTr_customer_address_id());

		Assert.assertEquals(customerAddressDBModel.getFlat_number(), customerAddress.flat_number());
		Assert.assertEquals(customerAddressDBModel.getApartment_name(), customerAddress.apartment_name());
		Assert.assertEquals(customerAddressDBModel.getArea(), customerAddress.area());
		Assert.assertEquals(customerAddressDBModel.getLandmark(), customerAddress.landmark());
		
		Assert.assertEquals(customerAddressDBModel.getState(), customerAddress.state());
		Assert.assertEquals(customerAddressDBModel.getStreet_name(), customerAddress.street_name());
		Assert.assertEquals(customerAddressDBModel.getCountry(), customerAddress.country());
		Assert.assertEquals(customerAddressDBModel.getPincode(), customerAddress.pincode());
		
		
		int tr_job_head_id = response.then().extract().body().jsonPath().getInt("data.id");
		MapJobProblemModel jobDataFromDB = MapJobProblemDao.getProblemDetails(tr_job_head_id);
		Assert.assertEquals(jobDataFromDB.getMst_problem_id(), createJobPayload.problems().get(0).id());
		Assert.assertEquals(jobDataFromDB.getRemark(), createJobPayload.problems().get(0).remark());
		
		JobHeadDBModel jobHeadDataFromDB = JobHeadDao.getDataFromJobHead(customerId);
		Assert.assertEquals(jobHeadDataFromDB.getMst_oem_id(), createJobPayload.mst_oem_id());
		Assert.assertEquals(jobHeadDataFromDB.getMst_platform_id(), createJobPayload.mst_platform_id());
		Assert.assertEquals(jobHeadDataFromDB.getMst_service_location_id(), createJobPayload.mst_service_location_id());
		Assert.assertEquals(jobHeadDataFromDB.getMst_warrenty_status_id(), createJobPayload.mst_warrenty_status_id());
		
		int productId = response.then().extract().body().jsonPath().getInt("data.tr_customer_product_id");
		CustomerProductDBModel customerProductDBModel = CustomerProductDao.getProductInfo(productId);
		Assert.assertEquals(customerProductDBModel.getImei1(), customerProduct.imei1());
		Assert.assertEquals(customerProductDBModel.getImei2(), customerProduct.imei2());
		Assert.assertEquals(customerProductDBModel.getSerial_number(), customerProduct.serial_number());
		Assert.assertEquals(customerProductDBModel.getDop(), customerProduct.dop());
		Assert.assertEquals(customerProductDBModel.getPopurl(), customerProduct.popurl());
		Assert.assertEquals(customerProductDBModel.getMst_model_id(), customerProduct.mst_model_id());
		
		
		
	}

}
