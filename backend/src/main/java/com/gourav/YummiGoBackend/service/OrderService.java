package com.gourav.YummiGoBackend.service;

import com.gourav.YummiGoBackend.io.OrderRequest;
import com.gourav.YummiGoBackend.io.OrderResponse;
import com.razorpay.RazorpayException;

import java.util.List;
import java.util.Map;

public interface OrderService {
    OrderResponse createOrderWithPayment(OrderRequest request) throws RazorpayException;

    void verifypayment(Map<String, String> paymentData, String status);

    List<OrderResponse> getUserOrders();

    void removeOrder(String orderId);

    List<OrderResponse> getOrdersOfAllUsers();

    void updateOrderStatus(String orderId, String status);
}
