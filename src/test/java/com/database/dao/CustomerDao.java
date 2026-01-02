package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.database.DatabaseManager;
import com.database.model.CustomerDBModel;

public class CustomerDao {
	// Executing the query for tr_customer table! which will get details of
	// customer!

	private static final String CUSTOMER_DETAILS_QUERY = """
			SELECT * from tr_customer where id= ?
			""";
	
	private CustomerDao() {
		
	}
	
	public static CustomerDBModel getCustomerInfo(int customerId) {
		CustomerDBModel customerDBModel = null;
		try {
		Connection conn = DatabaseManager.getConnection();
		PreparedStatement praparedStatement = conn.prepareStatement(CUSTOMER_DETAILS_QUERY);
		praparedStatement.setInt(1, customerId);
		ResultSet resultSet = praparedStatement.executeQuery();
		

		while (resultSet.next()) {
		
			customerDBModel = new CustomerDBModel(resultSet.getInt("id"), resultSet.getString("first_name"), resultSet.getString("last_name"),
					resultSet.getString("mobile_number"), resultSet.getString("mobile_number_alt"),
					resultSet.getString("email_id"), resultSet.getString("email_id_alt"), resultSet.getInt("tr_customer_address_id"));
		}
		}catch(SQLException e) {
			System.err.println(e.getMessage());
		}
		return customerDBModel;
	}

}
