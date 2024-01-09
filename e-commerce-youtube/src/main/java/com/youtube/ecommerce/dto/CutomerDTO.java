package com.youtube.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CutomerDTO {
    private Long id;
    private String fullName;
    private String phoneNumber;
}
