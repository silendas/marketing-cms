package com.cms.score.promotormanagement.dto;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BranchTargetDto {

    @NotBlank(message = "Branch harus diisi")
    private Long branchId;

    @NotBlank(message = "Target harus diisi")
    private Double target;

    @NotBlank(message = "Tanggal harus diisi")
    private Date date;
    
}
