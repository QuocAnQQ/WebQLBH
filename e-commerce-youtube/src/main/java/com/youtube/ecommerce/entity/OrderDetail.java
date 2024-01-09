package com.youtube.ecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_category_id")
    private Long productCategoryId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private Long price;

    @Column(name = "total_price")
    private Long totalPrice;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "time_sale")
    private Instant timeSale;

    private String orderStatus;


    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne
    private Product product;

    @OneToOne
    private User user;





}
