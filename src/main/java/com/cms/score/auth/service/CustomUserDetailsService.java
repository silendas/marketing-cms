package com.cms.score.auth.service;

import com.cms.score.config.jwt.JwtService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final RestTemplate restTemplate;
    private final JwtService jwtService;

    @Value("${siap.service.url}")
    private String siapServiceUrl;

    @Override
    public UserDetails loadUserByUsername(String token) throws UsernameNotFoundException {
        String nip = jwtService.extractUsername(token);
        //String token = jwtService.extractTokenSIAP(username);
        String url = this.siapServiceUrl + "/profile?nip=" + nip;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    Map.class);

            if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
                throw new UsernameNotFoundException("User not found");
            }

            return new org.springframework.security.core.userdetails.User(
                    nip,
                    "", // Password is set to null
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")) // Default authority
            );
        } catch (HttpClientErrorException e) {
            throw new UsernameNotFoundException("User not found", e);
        }
    }
}
