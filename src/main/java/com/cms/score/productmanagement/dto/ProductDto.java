package com.cms.score.productmanagement.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ProductDto {
    
    @NotBlank(message = "Product name cannot be empty")
    private String name;

    @NotNull(message = "Product type cannot be empty")
    private Long productTypeId;

}
