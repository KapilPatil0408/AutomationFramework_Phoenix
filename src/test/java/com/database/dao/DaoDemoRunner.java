package com.database.dao;

import java.util.ArrayList;
import java.util.List;

import com.api.record.model.CreateJobPayload;
import com.api.utils.CreateJobBeanMapper;
import com.dataproviders.api.bean.CreateJobBean;

public class DaoDemoRunner {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		List<CreateJobBean> beanList= CreateJobPayloadDataDao.getCreateJobPayloadData();
		
		List<CreateJobPayload> payloadList= new ArrayList<CreateJobPayload>();

		for( CreateJobBean createBean : beanList) {
			CreateJobPayload payload = CreateJobBeanMapper.mapper(createBean);
			payloadList.add(payload);
		}
		
		System.out.println("------------------------------");
		
		for(CreateJobPayload payload:payloadList) {
			System.out.println(payload);
		}
	}

}
