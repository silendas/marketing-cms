package com.cms.score.common.exception;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.cms.score.common.response.Response;
import com.cms.score.common.response.dto.GlobalDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtException implements AuthenticationEntryPoint {

@Override
    public void commence(jakarta.servlet.http.HttpServletRequest request,
            jakarta.servlet.http.HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        
        GlobalDto dto = new GlobalDto();
        dto.setStatus(HttpStatus.UNAUTHORIZED.value());
        dto.setMessage("Anda belum login");
        
        ResponseEntity<Object> responseEntity = Response.buildResponse(dto, 3);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), responseEntity.getBody());
    }

}
