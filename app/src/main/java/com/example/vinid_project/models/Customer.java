package com.example.vinid_project.models;

import androidx.annotation.NonNull;

public class Customer {
    int id;
    String name;
    String phoneNumber;
    String code;
    String email;
    String address;

    public Customer(int id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
    public Customer(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
    public Customer(String code,String name,String email, String phoneNumber,String address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.code = code;
        this.email = email;
        this.address = address;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
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
        return "Mã khách hàng: " + code
                + "\n" + "Tên khách hàng: " + name
                + "\n" + "Email: " + email
                + "\n" + "Số điện thoại: " + phoneNumber
                + "\n" + "Địa chỉ: " + address;
    }
}
