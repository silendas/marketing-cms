package com.cms.score.auth.dto;

import lombok.Data;

@Data
public class UserDto {
    private String jwt_token;
    private String nip;
    private String employee_name;
    private String employee_phone;
    private String employee_email;
    private Object organization_structure;
}
