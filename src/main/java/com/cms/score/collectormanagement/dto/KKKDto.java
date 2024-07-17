package com.cms.score.collectormanagement.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class KKKDto {

    @NotBlank(message = "Collector Id harus diisi")
    private Long collectorId;

    @NotNull(message = "Target harus diisi")
    private CollectorTargetDto collectorTarget;

    @NotNull(message = "Planning harus diisi")
    private List<CollectorPlanningDto> collectorPlanning;

}
