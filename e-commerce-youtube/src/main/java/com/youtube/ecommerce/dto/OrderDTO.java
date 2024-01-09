package com.youtube.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private Integer quantity;
    private Long price;
    private Long totalPrice;
    private Long productId;
    private  String productName;
    private Instant timeSale;
    private String customerPhone;
    private String customerName;
    private Long categoryId;
}