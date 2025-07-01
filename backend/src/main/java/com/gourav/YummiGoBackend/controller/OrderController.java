package com.gourav.YummiGoBackend.controller;

import com.gourav.YummiGoBackend.io.OrderRequest;
import com.gourav.YummiGoBackend.io.OrderResponse;
import com.gourav.YummiGoBackend.service.OrderService;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse createOrderWithPayment(@RequestBody OrderRequest request) throws RazorpayException {
        OrderResponse response = orderService.createOrderWithPayment(request);
        return response;
    }

    @PostMapping("/verify")
    public void verifyPayment(@RequestBody Map<String, String> paymentData) {
        orderService.verifypayment(paymentData, "paid");
    }

    @GetMapping
    public List<OrderResponse> getOrders() {
        return orderService.getUserOrders();
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable String orderId) {
        orderService.removeOrder(orderId);
    }


    //admin panel
    @GetMapping("/all")
    public List<OrderResponse> getOrdersOfAllUsers() {
        return orderService.getOrdersOfAllUsers();
    }

    //admin panel
    @PatchMapping("/status/{orderId}")
    public void updateOrderStatus(@PathVariable String orderId, @RequestParam String status){
        orderService.updateOrderStatus(orderId, status);
    }
}
