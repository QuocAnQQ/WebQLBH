package com.youtube.ecommerce.dto;

import com.youtube.ecommerce.entity.ImageModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private String productName;
    private Long productQuantity;
    private String productDescription;
    private double productPriceInput;
    private double productActualPrice;
    private Long productCategoryId;

    private MultipartFile productImages;

}
