package com.ems.AuthServer.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtTokenProvider {

    private final SecretKey secretKey;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKeyString) {
        this.secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessToken(String email, String userName, String role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 900000); 

        return Jwts.builder()
                .setSubject(email)
                .claim("userName", userName)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);  
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public String getEmail(String token) {
        Claims claims = getClaims(token);
        return claims != null ? claims.getSubject() : null;
    }

    public String getClaim(String token, String claimKey) {
        Claims claims = getClaims(token);
        return claims != null ? claims.get(claimKey, String.class) : null;
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                   .setSigningKey(secretKey)
                   .build()
                   .parseClaimsJws(token)
                   .getBody();
    }
}
