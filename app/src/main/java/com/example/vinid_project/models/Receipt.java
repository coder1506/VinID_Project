package com.example.vinid_project.models;

public class Receipt {
    int id;
    String name;
    String phoneNumber;
    String category;
    String money;
    String location;

    public Receipt(int id, String name, String phoneNumber, String category, String money, String location) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.category = category;
        this.money = money;
        this.location = location;
    }

    public Receipt(String name, String phoneNumber, String category, String money, String location) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.category = category;
        this.money = money;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Tên khách hàng: " + name
                + "\n" + "Số điện thoại: " + phoneNumber
                + "\n" + "Loại thanh toán: " + category
                + "\n" + "Số tiền thanh toán: " + money + " VNĐ"
                + "\n" + "Địa chỉ: " + location;
    }
}
