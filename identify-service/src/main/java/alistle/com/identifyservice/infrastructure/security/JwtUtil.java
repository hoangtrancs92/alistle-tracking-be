package alistle.com.identifyservice.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;


public class JwtUtil {
//    @Value("${jwt.secret}")
//    private String secretKey;
//
//    @Value("${jwt.expiration}")
//    private long expirationTime;
//
//    private SecretKey key;
//
//    @PostConstruct
//    public void init() {
//        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
//    }
//
//    public String generateToken(String userId) {
//        Instant now = Instant.now();
//        return Jwts.builder()
//                .subject(userId)
//                .issuedAt(Date.from(now))
//                .expiration(Date.from(now.plusMillis(expirationTime)))
//                .signWith(key)
//                .compact();
//    }
//
//    public String extractUserId(String token) {
//        return getClaims(token).getSubject();
//    }
//
//    public boolean validate(String token) {
//        try {
//            getClaims(token);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    private Claims getClaims(String token) {
//        return Jwts.parser()
//                .verifyWith(key)
//                .build()
//                .parseSignedClaims(token)
//                .getPayload();
//    }
}
