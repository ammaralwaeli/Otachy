package com.srit.otachy.database.models;

import java.io.Serializable;

public class UserVendorModel implements Serializable {

	private int id;
	private String username;
	private String mobileNumber;
	private String government;
	private String district;

	private static UserVendorModel instance;

	public static UserVendorModel getInstance() {
		return instance;
	}

	public static void setInstance(UserVendorModel instance) {
		UserVendorModel.instance = instance;
	}

	public int getId(){
		return id;
	}

	public String getUsername(){
		return username;
	}

	public String getMobileNumber(){
		return mobileNumber;
	}

	public String getGovernment(){
		return government;
	}

	public String getDistrict(){
		return district;
	}

	@Override
 	public String toString(){
		return 
			"UserVendorModel{" + 
			"id = '" + id + '\'' + 
			",username = '" + username + '\'' + 
			",mobileNumber = '" + mobileNumber + '\'' + 
			",government = '" + government + '\'' + 
			",district = '" + district + '\'' + 
			"}";
		}
}