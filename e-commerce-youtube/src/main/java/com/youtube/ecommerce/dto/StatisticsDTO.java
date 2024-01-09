package com.youtube.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatisticsDTO {
    private Long productId;
    private String productName;
    private double productPriceInput;
    private double productActualPrice;
    private double soldQuantity;
    private double profit;
}

