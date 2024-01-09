package com.youtube.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateDTO {
    private  Long id;
    private String productName;
    private Long productQuantity;
    private String productDescription;
    private double productActualPrice;
}
