package com.api.constant;

public enum ServiceLocation {

	SERVICE_LACTION_A(1),
	SERVICE_LACTION_B(2),
	SERVICE_LACTION_C(3);
	
	int code;
	
	ServiceLocation(int code){
		this.code= code;
	}
	
	public int getCode() {
		return code;
	}
}
