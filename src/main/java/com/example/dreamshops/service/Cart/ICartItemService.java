package com.example.dreamshops.service.Cart;

import com.example.dreamshops.model.CartItem;

public interface ICartItemService {
    void addItemToCart(Long cartId, Long productId, int quantity);
    void removeItemFromCart(Long cartId, Long productId);
    void updateItemInCart(Long cartId, Long productId, int quantity);

    CartItem getCartItem(Long cartId, Long productId);
}
