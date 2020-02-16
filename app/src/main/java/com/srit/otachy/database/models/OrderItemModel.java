package com.srit.otachy.database.models;

import java.io.Serializable;

public class OrderItemModel implements Serializable {
    private int itemId;
    private int categoryId;
    private int count;
    private String price;
    private String totalPrice;


    public OrderItemModel(int itemId, int categoryId, int count, String price, String totalPrice) {
        this.itemId = itemId;
        this.categoryId = categoryId;
        this.count = count;
        this.price = price;
        this.totalPrice = totalPrice;
    }

    public int getItemId() {
        return itemId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getCount() {
        return count;
    }

    public String getPrice() {
        return price;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return
                "OrderItemModel{" +
                        "itemId = '" + itemId + '\'' +
                        ",categoryId = '" + categoryId + '\'' +
                        ",count = '" + count + '\'' +
                        ",price = '" + price + '\'' +
                        ",totalPrice = '" + totalPrice + '\'' +
                        "}";
    }
}