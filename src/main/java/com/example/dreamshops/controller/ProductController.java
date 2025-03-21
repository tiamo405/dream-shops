package com.example.dreamshops.controller;

import com.example.dreamshops.exceptions.ResourceNotFoundException;
import com.example.dreamshops.model.Product;
import com.example.dreamshops.request.AddProductRequest;
import com.example.dreamshops.request.UpdateProductRequest;
import com.example.dreamshops.response.ApiResponse;
import com.example.dreamshops.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private  final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Success")
                .data(products)
                .build());
    }

    @GetMapping("/product/{id}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Success")
                    .data(product)
                    .build());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder()
                    .message(e.getMessage())
                    .build());
        }
    }

    @PutMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
        try {
            Product product1 = productService.addProduct(product);
            return ResponseEntity.ok(ApiResponse.builder()
                .message("Success")
                .data(product1)
                .build());
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                ApiResponse.builder()
                    .message(e.getMessage())
                    .build());
        }
    }

    @PutMapping("/product/{id}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody UpdateProductRequest request, @PathVariable Long id) {
        try {
            Product product = productService.updateProductById(request, id);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Success")
                    .data(product)
                    .build());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(
                ApiResponse.builder()
                    .message(e.getMessage())
                    .build()
            );
        }
    }

    @DeleteMapping("/product/{id}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProductById(id);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Success")
                    .build());
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(
                ApiResponse.builder()
                    .message(e.getMessage())
                    .build()
            );
        }
    }

    @GetMapping("/product/{name}/products")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name) {
        try {
            List<Product> products = productService.getProductsByName(name);
            if (products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder()
                        .message("Product not found")
                        .data(null)
                        .build());
            } else {
                return ResponseEntity.ok(ApiResponse.builder()
                        .message("Success")
                        .data(products)
                        .build());
            }
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                    ApiResponse.builder()
                            .message(e.getMessage())
                            .build()
            );
        }
    }

    @GetMapping("/product/by/category")
    public ResponseEntity<ApiResponse> getProductByCategory(@RequestParam String categoryName) {
        try {
            List<Product> products = productService.getProductsByCategory(categoryName);
            if (products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder()
                        .message("Product not found")
                        .data(null)
                        .build());
            } else {
                return ResponseEntity.ok(ApiResponse.builder()
                        .message("Success")
                        .data(products)
                        .build());
            }
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                    ApiResponse.builder()
                            .message(e.getMessage())
                            .build()
            );
        }
    }

    @GetMapping("/product/by/brand")
    public ResponseEntity<ApiResponse> getProductByBrand(@RequestParam String brandName) {
        try {
            List<Product> products = productService.getProductsByBrand(brandName);
            if (products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder()
                        .message("Product not found")
                        .data(null)
                        .build());
            } else {
                return ResponseEntity.ok(ApiResponse.builder()
                        .message("Success")
                        .data(products)
                        .build());
            }
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                    ApiResponse.builder()
                            .message(e.getMessage())
                            .build()
            );
        }
    }

    @GetMapping("/product/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String categoryName, @RequestParam String brandName) {
        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(categoryName, brandName);
            if (products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder()
                        .message("Product not found")
                        .data(null)
                        .build());
            } else {
                return ResponseEntity.ok(ApiResponse.builder()
                        .message("Success")
                        .data(products)
                        .build());
            }
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                    ApiResponse.builder()
                            .message(e.getMessage())
                            .build()
            );
        }
    }



    @GetMapping("/product/by/brand-and-name")
    public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName, @RequestParam String name) {
        try {
            List<Product> products = productService.getProductsByBrandAndName(brandName, name);
            if (products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder()
                        .message("Product not found")
                        .data(null)
                        .build());
            } else {
                return ResponseEntity.ok(ApiResponse.builder()
                        .message("Success")
                        .data(products)
                        .build());
            }
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                    ApiResponse.builder()
                            .message(e.getMessage())
                            .build());
        }
    }

    @GetMapping("/product/by/price-range") //getProductsByPriceRange
    public ResponseEntity<ApiResponse> getProductByPriceRange(@RequestParam BigDecimal minPrice, @RequestParam BigDecimal maxPrice) {
        try {
            List<Product> products = productService.getProductsByPriceRange(minPrice, maxPrice);
            if (products.isEmpty()){
                return ResponseEntity.status(NOT_FOUND).body(ApiResponse.builder()
                        .message("Product not found")
                        .data(null)
                        .build());
            } else {
                return ResponseEntity.ok(ApiResponse.builder()
                        .message("Success")
                        .data(products)
                        .build());
            }
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                    ApiResponse.builder()
                            .message(e.getMessage())
                            .build());
        }
    }

    @GetMapping("/product/count/by/brand-and-name")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brandName, @RequestParam String name) {
        try {
            Long count = productService.countProductsByBrandAndName(brandName, name);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Success")
                    .data(count)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                    ApiResponse.builder()
                            .message(e.getMessage())
                            .build());
        }
    }


}
