package com.youtube.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailDtO {
        private  Long id;
        private String productName;
        private Long productQuantity;
        private String productDescription;
        private double productPriceInput;
        private double productActualPrice;
        private Long productCategoryId;
        private String productImageURL; // Thêm đường dẫn ảnh
        // Các getter và setter

}
