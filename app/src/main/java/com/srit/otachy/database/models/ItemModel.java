package com.srit.otachy.database.models;

import java.io.Serializable;

public class ItemModel implements Serializable {
	private int id;
	private String name;
	private String description;

	public int getId(){
		return id;
	}

	public String getName(){
		return name;
	}

	public String getDescription(){
		return description;
	}

	@Override
 	public String toString(){
		return 
			"ItemModel{" + 
			"id = '" + id + '\'' + 
			",name = '" + name + '\'' + 
			",description = '" + description + '\'' + 
			"}";
		}
}