package com.cms.score.promotormanagement.dto;

import lombok.Data;

import java.util.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@Data
public class KKCDto {

    @NotBlank(message = "Branch harus diisi")
    private Long branchId;

    @NotEmpty(message = "Target harus diisi")
    private List<BranchTargetDto> branchTarget;
    
}
