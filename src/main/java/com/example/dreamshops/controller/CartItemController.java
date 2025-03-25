package com.example.dreamshops.controller;

import com.example.dreamshops.exceptions.ResourceNotFoundException;
import com.example.dreamshops.response.ApiResponse;
import com.example.dreamshops.service.Cart.ICartItemService;
import com.example.dreamshops.service.Cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    private final ICartItemService cartItemService;
    private final ICartService cartService;


    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam(required = false) Long cartId,
                                                     @RequestParam Long productId,
                                                     @RequestParam Integer quantity) {
        try {
            if(cartId == null){
                cartId = cartService.initializeNewCart();
            }
            cartItemService.addItemToCart(cartId, productId, quantity);
            return ResponseEntity.ok(ApiResponse.builder().message("Item added to cart").build());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("cart/{cartId}/item/{productId}/remove")
    public ResponseEntity<ApiResponse> removeProductFromCart(@PathVariable Long cartId,
                                                          @PathVariable Long productId) {
        try {
            cartItemService.removeProductFromCart(cartId, productId);
            return ResponseEntity.ok(ApiResponse.builder().message("Item removed from cart").build());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @PutMapping("cart/{cartId}/item/{productId}/update")
    public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId,
                                                          @PathVariable Long productId,
                                                          @RequestParam Integer quantity) {
        try {
            cartItemService.updateItemInCart(cartId, productId, quantity);
            return ResponseEntity.ok(ApiResponse.builder().message("Item updated in cart").build());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
