package com.cms.score.productmanagement.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductDto {
    
    @NotBlank(message = "Nama produk harus diisi")
    private String name;

    @NotNull(message = "Tipe Produk harus diisi")
    private Long productTypeId;

}
