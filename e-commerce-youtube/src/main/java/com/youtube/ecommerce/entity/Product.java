package com.youtube.ecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_quantity")
    private Long productQuantity;

    @Column(name = "product_description", length = 2000)
    private String productDescription;

    @Column(name = "product_price_input")
    private Double productPriceInput;

    @Column(name = "product_actual_price")
    private Double productActualPrice;


    @Column(name = "product_image", length = 2000)
    private String productImages;

    @ManyToOne
    @JoinColumn(name = "product_category_id")
    private ProductCategory productCategory;










    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }



    public Double getProductActualPrice() {
        return productActualPrice;
    }

    public void setProductActualPrice(Double productActualPrice) {
        this.productActualPrice = productActualPrice;
    }
}
