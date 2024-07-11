package com.cms.score.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cms.score.auth.dto.LoginDto;
import com.cms.score.auth.service.AuthService;
import com.cms.score.common.path.BasePath;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = BasePath.BASE_API)
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto dto) {
        return authService.login(dto);
    }

    @PutMapping("/logout")
    public ResponseEntity<Object> logout(@NonNull HttpServletRequest request) {
        final String tokenHeader = request.getHeader("Authorization");
        String jwtToken = tokenHeader.substring(7);
        return authService.logout(jwtToken);
    }
    
}
