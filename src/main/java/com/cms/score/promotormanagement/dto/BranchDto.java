package com.cms.score.promotormanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BranchDto {

    @NotBlank(message = "Nama branch harus diisi")
    private String name;
    
}
