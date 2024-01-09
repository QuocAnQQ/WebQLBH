package com.youtube.ecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "product_category_code")
    private String productCategoryCode;

    @Column(name = "product_category_name")
    private String productCategoryName;


    @OneToMany(mappedBy = "productCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;
}
