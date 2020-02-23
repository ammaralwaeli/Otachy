package com.srit.otachy.database.models;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class ServiceModel implements Serializable {

	private int id;
	private int price;
	private ItemModel item;

	private static ServiceModel instance;

	public static ServiceModel getInstance() {
		return instance;
	}

	public static void setInstance(ServiceModel instance) {
		ServiceModel.instance = instance;
	}

	public int getId(){
		return id;
	}

	public ItemModel getItem() {
		return item;
	}

	public int getPrice(){
		return price;
	}

	@NotNull
	@Override
	public String toString() {
		return "ServiceModel{" +
				"id=" + id +
				", price='" + price + '\'' +
				", item=" + item.toString() +
				'}';
	}
}