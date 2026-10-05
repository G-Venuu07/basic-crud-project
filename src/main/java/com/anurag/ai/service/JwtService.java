package com.anurag.ai.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
      private final String secret = "mysecretkey1234mysecretkey12345678";

      public SecretKey getKey() {
            return Keys.hmacShaKeyFor(secret.getBytes());
      }

      public String generateToken(String email) {
            return Jwts.builder()
                        .subject(email)
                        .issuedAt(new Date())
                        .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 20))
                        .signWith(getKey())
                        .compact();
      }

      public String extractUsername(String token) {
            return Jwts.parser()
                        .verifyWith(getKey())
                        .build()
                        .parseSignedClaims(token)
                        .getPayload()
                        .getSubject();
      }

      public boolean validateToken(String token) {
            try {
                  Jwts.parser()
                              .verifyWith(getKey())
                              .build()
                              .parseSignedClaims(token);
                  return true;
            } catch (Exception e) {
                  return false;
            }
      }
}
