package com.cms.score.productmanagement.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class KKPDto {

    @NotNull(message = "Target harus diisi")
    private ProductTargetDto productTarget;

    @NotEmpty(message = "Rencana harus diisi")
    private List<ProductPlanDto> productPlan;
}
