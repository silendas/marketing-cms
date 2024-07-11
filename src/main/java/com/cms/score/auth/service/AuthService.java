package com.cms.score.auth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.cms.score.auth.dto.ErrorResponse;
import com.cms.score.auth.dto.LoginDto;
import com.cms.score.auth.dto.LoginResponse;
import com.cms.score.common.response.Message;
import com.cms.score.common.response.Response;
import com.cms.score.common.response.dto.GlobalDto;
import com.cms.score.config.jwt.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${siap.service.url}")
    private String siapServiceUrl;

    public ResponseEntity<Object> login(LoginDto dto) {
        String url = this.siapServiceUrl + "/login";
        try {
            Map<String, String> mapObj = new LinkedHashMap<>();
            mapObj.put("username", dto.getUsername());
            mapObj.put("password", dto.getPassword());
            mapObj.put("app_id", "web");
            ResponseEntity<LoginResponse> responseEntity = restTemplate.postForEntity(url, mapObj, LoginResponse.class);

            LoginResponse response = responseEntity.getBody();

            if (response.getMeta().getStatus_code() != 200) {
                return Response.buildResponse(new GlobalDto(response.getMeta().getStatus_code(), null,
                        response.getMeta().getMessage(), null, null, List.of(response.getMeta().getBody())), 1);
            }

            // String jwtToken = jwtService.generateToken(response.getData().getUser());

            return Response.buildResponse(
                    new GlobalDto(Message.SUCCESSFULLY_LOGIN.getStatusCode(), response.getData().getUser().getJwt_token(),
                            Message.SUCCESSFULLY_LOGIN.getMessage(), null, null, null),
                    2);
        } catch (HttpClientErrorException e) {
            try {
                ErrorResponse errorResponse = objectMapper.readValue(e.getResponseBodyAsString(), ErrorResponse.class);
                List<String> errorMessages = errorResponse.getData().getError();
                return Response.buildResponse(
                        new GlobalDto(HttpStatus.BAD_REQUEST.value(), null, Message.FAILED_LOGIN.getMessage(),  null, null, errorMessages), 1);
            } catch (Exception ex) {
                return Response.buildResponse(new GlobalDto(Message.FAILED_LOGIN.getStatusCode(), null,
                        Message.FAILED_LOGIN.getMessage(),  null, null, List.of(ex.getLocalizedMessage())), 1);
            }
        } catch (Exception e) {
            return Response.buildResponse(new GlobalDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), null,
                    e.getMessage(), null, null, List.of(e.getLocalizedMessage())), 1);
        }
    }

    public ResponseEntity<Object> logout(String token) {
         String url = this.siapServiceUrl + "/logout";
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
            jwtService.blacklistToken(token);
            return Response.buildResponse(new GlobalDto(Message.SUCCESSFULLY_LOGOUT.getStatusCode(), null,
                    Message.SUCCESSFULLY_LOGOUT.getMessage(), null, null, null), 0);
        } catch (HttpClientErrorException e) {
            return Response.buildResponse(new GlobalDto(HttpStatus.BAD_REQUEST.value(), null,"Invalid token", null, null,null), 0);
        } catch (Exception e) {
            return Response.buildResponse(new GlobalDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), null,
                    e.getMessage(), null, null, List.of(e.getLocalizedMessage())), 1);
        }
    }
}
