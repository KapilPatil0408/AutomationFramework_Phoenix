package com.api.utils;

import java.util.Iterator;

import com.dataproviders.api.bean.CreateJobBean;

public class demo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub\
		
		Iterator<CreateJobBean> iterator= CSVReaderUtil.loadCSV("testData/CreateJobData.csv", CreateJobBean.class);
		
		while(iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		
		

	}

}
