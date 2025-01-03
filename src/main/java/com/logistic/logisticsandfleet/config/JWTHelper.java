package com.logistic.logisticsandfleet.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class JWTHelper {

    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // Use a secure key

    public static String generateToken(Long userId, String role) {
        return Jwts.builder().setSubject(userId + "," + role) // Combine userId and role in subject
                .setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + (24 * 60 * 60 * 1000)))
                .signWith(key).compact();

    }

    public static Claims validateToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public static Long getUserIdFromToken(String token) {
        String subject = validateToken(token).getSubject();
        return Long.parseLong(subject.split(",")[0]); // Extract userId
    }

    public static String getRoleFromToken(String token) {
        String subject = validateToken(token).getSubject();
        return subject.split(",")[1]; // Extract role
    }
}
