package com.example.bookAPI.security.jwt.util;

import com.example.bookAPI.security.jwt.token.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class JwtTokenizer {

    private final byte[] accessSecret;
    private final byte[] refreshSecret;

    public final static Long ACCESS_TOKEN_EXPIRE_COUNT = JwtProperties.ACCESS_TOKEN_EXPIRATION_TIME;
    public final static Long REFRESH_TOKEN_EXPIRE_COUNT = JwtProperties.REFRESH_TOKEN_EXPIRATION_TIME;

    public JwtTokenizer() {
        this.accessSecret = JwtProperties.SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        this.refreshSecret = JwtProperties.REFRESH_KEY.getBytes(StandardCharsets.UTF_8);
    }

    public String createAccessToken(Long id, String email, String name, List<String> roles) {
        return createToken(id, email, name, roles, ACCESS_TOKEN_EXPIRE_COUNT, accessSecret);
    }

    public String createRefreshToken(Long id, String email, String name, List<String> roles) {
        return createToken(id, email, name, roles, REFRESH_TOKEN_EXPIRE_COUNT, refreshSecret);
    }


    private String createToken(Long id, String email, String name, List<String> roles,
                               Long expire, byte[] secretKey) {
        Claims claims = Jwts.claims().setSubject(email);

        claims.put("roles", roles);
        claims.put("memberId", id);
        claims.put("name", name);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expire))
                .signWith(getSigningKey(secretKey))
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        String[] tokenArr = token.split(" ");
        token = tokenArr[1];
        Claims claims = parseToken(token, accessSecret);
        return Long.valueOf((Integer)claims.get("memberId"));
    }

    public Claims parseAccessToken(String accessToken) {
        return parseToken(accessToken, accessSecret);
    }

    public Claims parseRefreshToken(String refreshToken) {
        return parseToken(refreshToken, refreshSecret);
    }


    public Claims parseToken(String token, byte[] secretKey) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public static Key getSigningKey(byte[] secretKey) {
        return Keys.hmacShaKeyFor(secretKey);
    }
}