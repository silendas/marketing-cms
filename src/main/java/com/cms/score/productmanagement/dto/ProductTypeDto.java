package com.cms.score.productmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProductTypeDto {

    @NotBlank(message = "Nama harus diisi")
    private String name;
    
}
