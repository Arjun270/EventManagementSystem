package com.ems.Gateway.Utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class JwtValidator {

    private static final Logger logger = LoggerFactory.getLogger(JwtValidator.class);

    @Value("${jwt.secret}")
    private String secret;

    private byte[] secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Base64.getEncoder().encode(secret.getBytes());
    }

    public boolean validateToken(String token) {
        try {
            logger.debug("Starting token validation");
            logger.debug("Token to validate: {}", token);

            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);

            logger.info("JWT token validated successfully");
            return true;
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
            return false;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
            return false;
        } catch (Exception e) {
            logger.error("Invalid JWT token: {} - {}", e.getClass().getName(), e.getMessage());
            return false;
        }
    }

    private Claims getClaims(String token) {
        try {
            logger.debug("Extracting claims from token");
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            logger.debug("Claims extracted successfully: {}", claims);
            return claims;
        } catch (Exception ex) {
            logger.error("Error extracting claims: {} - {}", ex.getClass().getName(), ex.getMessage());
            return null;
        }
    }

    public String getEmail(String token) {
        Claims claims = getClaims(token);
        if (claims != null) {
            Object email = claims.get("email");
            return email != null ? email.toString() : null;
        }
        return null;
    }

    public String getClaim(String token, String claimKey) {
        Claims claims = getClaims(token);
        if (claims != null) {
            Object value = claims.get(claimKey);
            return value != null ? value.toString() : null;
        }
        return null;
    }
}