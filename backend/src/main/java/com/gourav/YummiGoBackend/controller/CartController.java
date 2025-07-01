package com.gourav.YummiGoBackend.controller;

import com.gourav.YummiGoBackend.io.CartRequest;
import com.gourav.YummiGoBackend.io.CartResponse;
import com.gourav.YummiGoBackend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin("*")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping
    public CartResponse addToCart(@RequestBody CartRequest request) {
        String foodId = request.getFoodId();
        if (foodId == null || foodId.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"FoodId not found");
        }
        return cartService.addToCart(request);
    }

    @GetMapping
    public CartResponse getCart(){
        return cartService.getCart();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearCart(){
        cartService.clearCart();
    }

    @PostMapping("/remove")
    public CartResponse removeFromCart(@RequestBody CartRequest request) {
        String foodId = request.getFoodId();
        if (foodId == null || foodId.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"FoodId not found");
        }
        return  cartService.removeFromCart(request);
    }

}
