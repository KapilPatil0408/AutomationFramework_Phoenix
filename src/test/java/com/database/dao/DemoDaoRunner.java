package com.database.dao;

import java.sql.SQLException;

import com.database.model.CustomerProductDBModel;

public class DemoDaoRunner {

	public static void main(String[] args) throws SQLException {

		CustomerProductDBModel customerProductDBModel= CustomerProductDao.getProductInfo(143045);
		System.out.println(customerProductDBModel);
	}
}
