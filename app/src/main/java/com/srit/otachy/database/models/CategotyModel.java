package com.srit.otachy.database.models;

import java.io.Serializable;

public class CategotyModel implements Serializable {
	private int id;
	private String name;
	private String description;
	private String imageUri;

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