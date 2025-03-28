package com.example.dreamshops.service.Cart;

import com.example.dreamshops.model.Cart;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long cartId);
    void clearCart(Long cartId);
    BigDecimal getTotalPrice(Long cartId);


    Long initializeNewCart();

    Cart getCartByUserId(Long userId);
}
