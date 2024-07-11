package com.cms.score.productmanagement.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductTypeDto {

    @NotBlank(message = "Product type name cannot be empty")
    private String name;
    
}
