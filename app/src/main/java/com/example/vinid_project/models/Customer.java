package com.example.vinid_project.models;

import androidx.annotation.NonNull;

public class Customer {
    int id;
    String name;
    String phoneNumber;

    public Customer(int id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Customer(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @NonNull
    @Override
    public String toString() {
        return "Tên khách hàng: " + name
                + "\n" + "Số điện thoại: " + phoneNumber;
    }
}
