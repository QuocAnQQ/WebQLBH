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
public class ReturnOrderDTO {
    private  Long id;
    private Long productId;
    private Instant dateReturn;
    private  String productName;
    private Long quantity;
    private String reasonReturn;
    private Long customerId;
    private String customerName;

}
