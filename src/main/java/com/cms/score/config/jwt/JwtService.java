package com.cms.score.config.jwt;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.cms.score.auth.dto.UserDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

    @Value("${jwt.secret.siap}")
    private String secret; 

    private final List<String> blacklist = new ArrayList<>();

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token) && !isTokenBlacklisted(token);
    }

    // public String extractNip(String token) {
    //     return extractUsername(token);
    // }

    // public String extractTokenSIAP(String token) {
    //     Claims claims = extractAllClaims(token);
    //     return claims.get("siapToken").toString();
    // }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public void blacklistToken(String token) {
        blacklist.add(token);
    }

    public boolean isTokenBlacklisted(String token) {
        return blacklist.contains(token);
    }

    public void clearBlacklist() {
        blacklist.clear();
    }

    // public String generateToken(UserDto user) {
    //     Map<String, Object> extraClaim = new HashMap<>();
    //     extraClaim.put("siapToken", user.getJwt_token());
    //     return Jwts.builder()
    //             .setClaims(extraClaim)
    //             .setSubject(user.getNip())
    //             .setIssuedAt(new Date(System.currentTimeMillis()))
    //             .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours expiry
    //             .signWith(SignatureAlgorithm.HS256, Base64.getDecoder().decode(secret))
    //             .compact();
    // }
}
