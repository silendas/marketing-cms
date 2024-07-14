package com.cms.score.productmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PTPDto {

    @NotBlank(message = "Product Target Id harus diisi")
    private Long productTargetId;

    @NotBlank(message = "Product Plan Id harus diisi")
    private Long productPlanId;
    
}
