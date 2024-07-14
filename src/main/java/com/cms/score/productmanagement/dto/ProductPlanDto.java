package com.cms.score.productmanagement.dto;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProductPlanDto {

    @NotBlank(message = "Target harus diisi")
    @Positive(message = "Target harus bernilai positif")
    private Double target;

    @NotBlank(message = "Tanggal harus diisi")
    private Date date;
}
