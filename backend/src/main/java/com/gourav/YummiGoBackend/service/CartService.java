package com.gourav.YummiGoBackend.service;

import com.gourav.YummiGoBackend.io.CartRequest;
import com.gourav.YummiGoBackend.io.CartResponse;

public interface CartService {
    CartResponse addToCart(CartRequest request);

    CartResponse getCart();

    void clearCart();

    CartResponse removeFromCart(CartRequest cartRequest);
}
