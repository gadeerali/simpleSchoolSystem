package com.myboot.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;


@Service
public class JwtAuth {
    @Value("${security.jwt.secret}")
    private String secretKey;

    @Value("${security.jwt.expiration-minutes}")
    private long expirationMinutes;

   private SecretKey getSingingKey(){
       byte[] keyBytes = secretKey.getBytes();
       return Keys.hmacShaKeyFor(keyBytes);
   }

   public String generateToken(String username){
       long expirationTime = expirationMinutes * 60 * 1000;
       return Jwts.builder()
               .subject(username)
               .issuedAt(new Date())
               .expiration(new Date(System.currentTimeMillis() + expirationTime))
               .signWith(getSingingKey())
               .compact();
   }

   public String extractUsername(String token){
       return Jwts.parser()
               .verifyWith(getSingingKey())
               .build()
               .parseSignedClaims(token)
               .getPayload()
               .getSubject();
   }


}
