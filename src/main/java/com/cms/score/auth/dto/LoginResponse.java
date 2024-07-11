package com.cms.score.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponse {
    private LoginApiData data;
    private LoginApiMeta meta;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LoginApiData {
        private UserDto user;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LoginApiMeta {
        private String message;
        private String body;
        private int status_code;
    }

}
