package com.example.dreamshops.service.Cart;

import com.example.dreamshops.exceptions.ResourceNotFoundException;
import com.example.dreamshops.model.Cart;
import com.example.dreamshops.model.User;
import com.example.dreamshops.repository.CartItemRepository;
import com.example.dreamshops.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor // Lombok annotation to generate constructor with all required arguments
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final AtomicLong cartIdGenerator = new AtomicLong(0);

    @Override
    public Cart getCart(Long Id) {
        Cart cart = cartRepository.findById(Id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + Id));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Transactional
    @Override
    public void clearCart(Long Id) {
        Cart cart = getCart(Id);
        cartItemRepository.deleteAllByCartId(Id);
        cart.clearCart();
//        cart.getTotalAmount(0);
        cartRepository.deleteById(Id);

    }

    @Override
    public BigDecimal getTotalPrice(Long cartId) {
        Cart cart = getCart(cartId);
        return cart.getTotalAmount();
    }

    @Override
    public Cart initializeNewCart(User user) {
        return Optional.ofNullable(getCartByUserId(user.getId()))
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }
}
