package com.example.vinid_project.models;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class ShopItem implements Serializable {
    int id;
    private String nameItem;
    private String priceItem;
    private String count;

    public ShopItem(int id, String nameItem, String priceItem, String count) {
        this.id = id;
        this.nameItem = nameItem;
        this.priceItem = priceItem;
        this.count = count;
    }

    public ShopItem(String nameItem, String priceItem, String count) {
        this.nameItem = nameItem;
        this.priceItem = priceItem;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameItem() {
        return nameItem;
    }

    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
    }

    public String getPriceItem() {
        return priceItem;
    }

    public void setPriceItem(String priceItem) {
        this.priceItem = priceItem;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @NonNull
    @Override
    public String toString() {
        return "Tên mặt hàng: " + nameItem
                + "\n" + "Giá: " + priceItem;
    }
}
