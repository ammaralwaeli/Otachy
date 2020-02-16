package com.srit.otachy.database.models;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class CategotyModel implements Serializable {


	private int id;
	private String name;
	private String description;
	private String imageUri;

	private static CategotyModel instance;

	public static CategotyModel getInstance() {
		return instance;
	}

	public static void setInstance(CategotyModel instance) {
		CategotyModel.instance = instance;
	}

	public int getId(){
		return id;
	}

	public String getName(){
		return name;
	}

	public String getDescription(){
		return description;
	}

	public String getImageUri(){
		return imageUri;
	}

	@NotNull
	@Override
 	public String toString(){
		return 
			"CategotyModel{" + 
			"id = '" + id + '\'' + 
			",name = '" + name + '\'' + 
			",description = '" + description + '\'' + 
			",imageUri = '" + imageUri + '\'' + 
			"}";
		}
}