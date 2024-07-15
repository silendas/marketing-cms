package com.cms.score.promotormanagement.dto;

import java.util.List;

import lombok.Data;

@Data
public class ResponseKKCDto {
    private Long id;
    private String name;
    private List<ResponseTargetDto> branchTargets;
}
