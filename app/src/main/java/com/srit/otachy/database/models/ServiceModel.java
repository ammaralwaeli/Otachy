package com.srit.otachy.database.models;

import java.io.Serializable;

public class ServiceModel implements Serializable {

	private int id;
	private double price;
	private ItemModel item;

	public int getId(){
		return id;
	}

	public ItemModel getItem() {
		return item;
	}

	public double getPrice(){
		return price;
	}

	@Override
	public String toString() {
		return "ServiceModel{" +
				"id=" + id +
				", price='" + price + '\'' +
				", item=" + item.toString() +
				'}';
	}
}