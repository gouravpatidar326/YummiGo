package com.gourav.YummiGoBackend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String userId;
    private String userAddress;
    private String phoneNumber;
    private String email;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<OrderItem> orderItems;

    private double amount;
    private String paymentStatus;
    private String razorpayOrderId;
    private String razorpaySignature;
    private String razorpayPaymentId;
    private String orderStatus;

    public OrderEntity() {
    }

    public OrderEntity(String userAddress, double amount, List<OrderItem> orderItems, String email, String phoneNumber, String orderStatus) {
        this.userAddress = userAddress;
        this.amount = amount;
        this.orderItems = orderItems;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.orderStatus = orderStatus;
    }

    public OrderEntity(String id, String userId, String userAddress, String phoneNumber, String email, List<OrderItem> orderItems, double amount, String paymentStatus, String razorpayOrderId, String razorpaySignature, String orderStatus) {
        this.id = id;
        this.userId = userId;
        this.userAddress = userAddress;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.orderItems = orderItems;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.razorpayOrderId = razorpayOrderId;
        this.razorpaySignature = razorpaySignature;
        this.orderStatus = orderStatus;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getRazorpayOrderId() {
        return razorpayOrderId;
    }

    public void setRazorpayOrderId(String razorpayOrderId) {
        this.razorpayOrderId = razorpayOrderId;
    }

    public String getRazorpaySignature() {
        return razorpaySignature;
    }

    public void setRazorpaySignature(String razorpaySignature) {
        this.razorpaySignature = razorpaySignature;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getRazorpayPaymentId() {
        return razorpayPaymentId;
    }

    public void setRazorpayPaymentId(String razorpayPaymentId) {
        this.razorpayPaymentId = razorpayPaymentId;
    }
}
