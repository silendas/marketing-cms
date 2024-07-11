package com.cms.score.auth.dto;

import lombok.Data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {
    private DataContent data;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataContent {
        private List<String> error;
    }
}
