package com.srit.otachy.database.models;

import org.threeten.bp.LocalDateTime;

import java.io.Serializable;
import java.util.List;

public class OrderModel implements Serializable {
	private int userId;
	private int vendorId;
	private int vendorUserId;
	private LocalDateTime receiveDate;
	private LocalDateTime deleverDate;
	private String totalPrice;
	private String descrition;
	private List<OrderItemModel> createOrderItemModels;


	private static OrderModel instance;

	public static OrderModel getInstance() {
		return instance;
	}

	public static void setInstance(OrderModel instance) {
		OrderModel.instance = instance;
	}

	public OrderModel(int userId, int vendorId, int vendorUserId, LocalDateTime receiveDate, LocalDateTime deleverDate, String totalPrice, String descrition, List<OrderItemModel> createOrderItemModels) {
		this.userId = userId;
		this.vendorId = vendorId;
		this.vendorUserId = vendorUserId;
		this.receiveDate = receiveDate;
		this.deleverDate = deleverDate;
		this.totalPrice = totalPrice;
		this.descrition = descrition;
		this.createOrderItemModels = createOrderItemModels;
	}

	public void setReceiveDate(LocalDateTime receiveDate) {
		this.receiveDate = receiveDate;
	}

	public void setDeleverDate(LocalDateTime deleverDate) {
		this.deleverDate = deleverDate;
	}

	public void setDescrition(String descrition) {
		this.descrition = descrition;
	}

	public int getUserId(){
		return userId;
	}

	public int getVendorId(){
		return vendorId;
	}

	public int getVendorUserId(){
		return vendorUserId;
	}

	public LocalDateTime getReceiveDate(){
		return receiveDate;
	}

	public LocalDateTime getDeleverDate(){
		return deleverDate;
	}

	public String getTotalPrice(){
		return totalPrice;
	}

	public String getDescrition(){
		return descrition;
	}

	public List<OrderItemModel> getCreateOrderItemModels() {
		return createOrderItemModels;
	}

	@Override
	public String toString() {
		return "OrderModel{" +
				"userId=" + userId +
				", vendorId=" + vendorId +
				", vendorUserId=" + vendorUserId +
				", receiveDate='" + receiveDate + '\'' +
				", deleverDate='" + deleverDate + '\'' +
				", totalPrice='" + totalPrice + '\'' +
				", descrition='" + descrition + '\'' +
				", createOrderItemModels=" + createOrderItemModels.toString() +
				'}';
	}
}