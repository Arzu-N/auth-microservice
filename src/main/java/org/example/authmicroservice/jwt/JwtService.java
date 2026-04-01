package org.example.authmicroservice.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.example.authmicroservice.dao.entity.UserEntity;
import org.example.authmicroservice.dto.TokenResponse;
import org.example.authmicroservice.redis.RedisService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final RedisService redisService;
    @Value("${jwt.secret}")
    private byte[] secret;

    public String generateAccessToken(UserEntity user) {
        String token = Jwts.builder()
                .subject(user.getUserName())
                .signWith(secretKeyMethod())
                .expiration(new Date(System.currentTimeMillis() + 60000))
                .claim("role", user.getRole())
                .compact();
        redisService.saveAccessToken(user.getUserName(),token);
        return token;
    }
    
    public String generateRefreshToken(String userName){
        String token = Jwts.builder()
                .subject(userName)
                .signWith(secretKeyMethod())
                .expiration(new Date(System.currentTimeMillis() + 120000))
                .compact();
        redisService.saveRefreshToken(userName,token);
        return token;
    }

    public Claims extractBody(String token){
        return Jwts.parser()
                .verifyWith((SecretKey) secretKeyMethod())
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    public String extractUsername(String token){
        return extractBody(token).getSubject();
    }


    private Key secretKeyMethod() {
        return Keys.hmacShaKeyFor(secret);
    }
}
