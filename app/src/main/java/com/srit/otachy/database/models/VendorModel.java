package com.srit.otachy.database.models;

import java.io.Serializable;

public class VendorModel implements Serializable {
	private String name;
	private String mobileNumber;
	private String government;
	private String district;
	private UserVendorModel user;

	public UserVendorModel getUser() {
		return user;
	}

	public String getName(){
		return name;
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
	public String toString() {
		return "VendorModel{" +
				"name='" + name + '\'' +
				", mobileNumber='" + mobileNumber + '\'' +
				", government='" + government + '\'' +
				", district='" + district + '\'' +
				", user=" + user.toString() +
				'}';
	}
}