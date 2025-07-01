package com.gourav.YummiGoBackend.impl;

import com.gourav.YummiGoBackend.entity.OrderEntity;
import com.gourav.YummiGoBackend.io.OrderRequest;
import com.gourav.YummiGoBackend.io.OrderResponse;
import com.gourav.YummiGoBackend.repo.CartRepo;
import com.gourav.YummiGoBackend.repo.OrderRepo;
import com.gourav.YummiGoBackend.service.OrderService;
import com.gourav.YummiGoBackend.service.UserService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private CartRepo cartRepo;

    @Value("${razorpay_key}")
    private String RAZORPAY_KEY;
    @Value("${razorpay_secret}")
    private String RAZORPAY_SECRET;

    @Override
    public OrderResponse createOrderWithPayment(OrderRequest request) throws RazorpayException {
        OrderEntity newOrder = convertToEntity(request);
        newOrder = orderRepo.save(newOrder);

        //create razorpay payment order
        RazorpayClient razorpayClient = new RazorpayClient(RAZORPAY_KEY, RAZORPAY_SECRET);
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", newOrder.getAmount() * 100 );
        orderRequest.put("currency", "INR");
        orderRequest.put("payment_capture", 1);

        try {
            Order razorpayOrder = razorpayClient.orders.create(orderRequest);
            newOrder.setRazorpayOrderId(razorpayOrder.get("id"));
        } catch (RazorpayException e) {
            System.out.println("Raw message: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }

        String loggedInUserId = userService.findByUserId();
        newOrder.setUserId(loggedInUserId);

        newOrder = orderRepo.save(newOrder);

        return convertToResponse(newOrder);
    }

    @Override
    @Transactional
    public void verifypayment(Map<String, String> paymentData, String status) {
        String razorpayOrderId = paymentData.get("razorpay_order_id");
        OrderEntity existingOrder = orderRepo.findByRazorpayOrderId(razorpayOrderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        existingOrder.setPaymentStatus(status);
        existingOrder.setRazorpaySignature(paymentData.get("razorpay_signature"));
        existingOrder.setRazorpayPaymentId(paymentData.get("razorpay_payment_id"));
        orderRepo.save(existingOrder);
        if ("paid".equalsIgnoreCase(status)) {
            cartRepo.deleteByUserId(existingOrder.getUserId());
        }
    }

    @Override
    public List<OrderResponse> getUserOrders() {
        String loggedInUserId = userService.findByUserId();
        List<OrderEntity> list = orderRepo.findByUserId(loggedInUserId);
        return list.stream().map(entity -> convertToResponse(entity)).collect(Collectors.toList());
    }

    @Override
    public void removeOrder(String orderId) {
        orderRepo.deleteById(orderId);
    }

    @Override
    public List<OrderResponse> getOrdersOfAllUsers() {
        List<OrderEntity> list = orderRepo.findAll();
        return list.stream().map(entity -> convertToResponse(entity)).collect(Collectors.toList());
    }

    @Override
    public void updateOrderStatus(String orderId, String status) {
        OrderEntity entity = orderRepo.findById(orderId)
                .orElseThrow(()-> new RuntimeException("Order not found"));
        entity.setOrderStatus(status);
        orderRepo.save(entity);
    }

    private OrderResponse convertToResponse(OrderEntity newOrder) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(newOrder.getId());
        orderResponse.setAmount(newOrder.getAmount());
        orderResponse.setUserAddress(newOrder.getUserAddress());
        orderResponse.setUserId(newOrder.getUserId());
        orderResponse.setRazorpayOrderId(newOrder.getRazorpayOrderId());
        orderResponse.setPaymentStatus(newOrder.getPaymentStatus());
        orderResponse.setOrderStatus(newOrder.getOrderStatus());
        orderResponse.setPhoneNumber(newOrder.getPhoneNumber());
        orderResponse.setEmail(newOrder.getEmail());
        orderResponse.setOrderItems(newOrder.getOrderItems());
        return orderResponse;
    }

    private OrderEntity convertToEntity(OrderRequest request) {
        return new OrderEntity(
                request.getUserAddress(),
                request.getAmount(),
                request.getOrderItems(),
                request.getEmail(),
                request.getPhoneNumber(),
                request.getOrderStatus()
        );
    }
}
