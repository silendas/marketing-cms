package com.cms.score.collectormanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CollectorTargetDto {

    @NotBlank(message = "Master harus diisi")
    private Double master;

    @NotBlank(message = "Menior harus diisi")
    private Double senior;

    @NotBlank(message = "Junior harus diisi")
    private Double junior;

    @NotBlank(message = "Training harus diisi")
    private Double training;

}