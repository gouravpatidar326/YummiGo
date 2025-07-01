package com.gourav.YummiGoBackend.io;

import com.gourav.YummiGoBackend.entity.OrderItem;

import java.util.List;


public class OrderRequest {

    private List<OrderItem> orderItems;
    private String userAddress;
    private double amount;
    private String email;
    private String phoneNumber;
    private String orderStatus;

    public OrderRequest() {
    }

    public OrderRequest(List<OrderItem> orderItems, String userAddress, double amount, String email, String phoneNumber, String orderStatus) {
        this.orderItems = orderItems;
        this.userAddress = userAddress;
        this.amount = amount;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.orderStatus = orderStatus;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
