package com.example.dreamshops.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDto {
    private Long id;
    private Integer quantity;
    private BigDecimal unitPrice;
    private ProductDto product;
}
