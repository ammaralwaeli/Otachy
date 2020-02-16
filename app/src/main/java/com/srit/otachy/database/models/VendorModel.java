package com.srit.otachy.database.models;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class VendorModel implements Serializable {
	private String id;
	private String name;
	private String mobileNumber;
	private String government;
	private String district;
	private UserVendorModel user;

	private static VendorModel instasne;

	public static VendorModel getInstance(){
		return instasne;
	}

	public static void setInstance(VendorModel vendorModel){
		instasne=vendorModel;
	}

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

	public String getId() {
		return id;
	}

	@NotNull
	@Override
	public String toString() {
		return "VendorModel{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", mobileNumber='" + mobileNumber + '\'' +
				", government='" + government + '\'' +
				", district='" + district + '\'' +
				", user=" + user +
				'}';
	}
}