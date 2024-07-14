package com.cms.score.productmanagement.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProductTargetDto {

    @NotNull(message = "Produk harus diisi")
    private Long productId;

    @NotNull(message = "Target harus diisi")
    @Positive(message = "Target harus bernilai positif")
    private Double target;

    @NotNull(message = "Pencapaian harus diisi")
    @Positive(message = "Pencapaian harus bernilai positif")
    private Double achievement;
}
