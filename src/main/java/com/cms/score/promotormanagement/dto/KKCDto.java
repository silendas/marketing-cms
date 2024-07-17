package com.cms.score.promotormanagement.dto;

import lombok.Data;

import java.util.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Data
public class KKCDto {

    @NotNull(message = "Branch harus diisi")
    private Long branchId;

    @NotBlank(message = "Percentage harus diisi")
    private Double percentage;

    @NotEmpty(message = "Target harus diisi")
    private List<BranchTargetDto> branchTarget;
    
}
