package com.example.dreamshops.request;

import com.example.dreamshops.model.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductRequest {
    private Long id;
    private String name;
    private  String brand;
    private String description;
    private BigDecimal price;
    private int inventory;

    private Category category;
}
