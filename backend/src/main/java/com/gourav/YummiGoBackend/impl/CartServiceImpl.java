package com.gourav.YummiGoBackend.impl;

import com.gourav.YummiGoBackend.entity.CartEntity;
import com.gourav.YummiGoBackend.io.CartRequest;
import com.gourav.YummiGoBackend.io.CartResponse;
import com.gourav.YummiGoBackend.repo.CartRepo;
import com.gourav.YummiGoBackend.service.CartService;
import com.gourav.YummiGoBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private UserService userService;


    @Override
    public CartResponse addToCart(CartRequest request) {
        String loggedInUserId = userService.findByUserId();
        Optional<CartEntity> cartOptional = cartRepo.findByUserId(loggedInUserId);
        CartEntity cart = cartOptional.orElseGet(() -> new CartEntity(loggedInUserId, new HashMap<>()));
        Map<String, Integer> cartItems = cart.getItems();
        cartItems.put(request.getFoodId(), cartItems.getOrDefault(request.getFoodId(), 0) +1);
        cart.setItems(cartItems);
        cart = cartRepo.save(cart);
        return convertToResponse(cart);
    }

    @Override
    public CartResponse getCart() {
        String loggedInUserId = userService.findByUserId();
        CartEntity entity = cartRepo.findByUserId(loggedInUserId)
                .orElse(new CartEntity(null,loggedInUserId,new HashMap<>()));
        return convertToResponse(entity);
    }

    @Override
    @Transactional
    public void clearCart() {
        String loggedInUserId = userService.findByUserId();
        cartRepo.deleteByUserId(loggedInUserId);
    }

    @Override
    public CartResponse removeFromCart(CartRequest cartRequest) {
        String loggedInUserId = userService.findByUserId();
        CartEntity entity = cartRepo.findByUserId(loggedInUserId)
                .orElseThrow(()-> new RuntimeException("Cart is not found"));
        Map<String, Integer> cartItems = entity.getItems();
        if (cartItems.containsKey(cartRequest.getFoodId())) {
            int currentQty = cartItems.get(cartRequest.getFoodId());
            if (currentQty > 0) {
                cartItems.put(cartRequest.getFoodId(), currentQty - 1);
            } else {
                cartItems.remove(cartRequest.getFoodId());
            }
            entity = cartRepo.save(entity);
        }
        return convertToResponse(entity);
    }

    private CartResponse convertToResponse(CartEntity cartEntity){
        return new CartResponse(
                cartEntity.getId(),
                cartEntity.getUserId(),
                cartEntity.getItems()
        );
    }
}
