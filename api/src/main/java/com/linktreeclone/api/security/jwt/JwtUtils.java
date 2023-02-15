package com.linktreeclone.api.security.jwt;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.linktreeclone.api.security.service.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    private int jwtExpirationMs;

    private Key secretKey;

    // Inject jwtExpirationMs and secretKey after instantiation
    public JwtUtils(
        @Value("${app.jwtSecret}") String jwtSecret,
        @Value("${app.jwtExpirationMs}") int jwtExpirationMs
    ) {
        this.jwtExpirationMs = jwtExpirationMs;
        this.secretKey = new SecretKeySpec(
            Base64.getDecoder().decode(jwtSecret), 
            SignatureAlgorithm.HS512.getJcaName());
    }

    public String generateJwtToken(Authentication auth) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) auth.getPrincipal();

        return Jwts.builder()
            .setSubject(userPrincipal.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date().getTime() + jwtExpirationMs)))
            .signWith(secretKey)
            .compact();
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
