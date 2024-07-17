package com.cms.score.collectormanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CollectorsDto {

    @NotBlank(message = "Branch harus diisi")
    private Long branchId;

    @NotBlank(message = "Total Collectors harus diisi")
    private Long totalCollectors;
    
}
