package com.cms.score.productmanagement.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class KKPDto {

    @NotNull(message = "Product harus diisi")
    private Long productId;
    
    @NotNull(message = "Target harus diisi")
    @Positive(message = "Target harus bernilai positif")
    private Double target;

    @NotNull(message = "Achievement harus diisi")
    @Positive(message = "Achievement harus bernilai positif")
    private Double achievement;

    @NotEmpty(message = "Rencana harus diisi")
    private List<ProductPlanDto> productPlan;
}
