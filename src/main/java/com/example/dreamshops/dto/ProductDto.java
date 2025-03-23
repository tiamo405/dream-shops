package com.example.dreamshops.dto;

import com.example.dreamshops.model.Category;
import com.example.dreamshops.model.Image;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Data
//@NoArgsConstructor // Lombok annotation to generate no-args constructor
                    // tieng viet: tao constructor khong co tham so
public class ProductDto {
    private String name;
    private  String brand;
    private String description;
    private BigDecimal price;
    private int inventory;
    private Category category;
    private List<ImageDto> images;
}
