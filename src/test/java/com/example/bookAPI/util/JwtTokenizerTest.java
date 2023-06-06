package com.example.bookAPI.util;


import com.example.bookAPI.security.jwt.util.JwtTokenizer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class JwtTokenizerTest {

    @Autowired
    JwtTokenizer jwtTokenizer;

    @Value("${jwt.secretKey}")
    String accessSecret;

    public final Long ACCESS_TOKEN_EXPIRE_COUNT = 30 * 60 * 1000L;

    @Test
    public void createToken() throws  Exception{
        String email = "mimnjae2246@gmail.com";
        List<String> roles = List.of("ROLE_USER");
        Long id = 1L;
        Claims claims = Jwts.claims().setSubject(email);

        claims.put("roles", roles);
        claims.put("userId", id);

        byte[] accessSecret = this.accessSecret.getBytes(StandardCharsets.UTF_8);

        // jwt 생성하는 부분
        String JwtToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + this.ACCESS_TOKEN_EXPIRE_COUNT))
                .signWith(Keys.hmacShaKeyFor(accessSecret))
                .compact();

        System.out.println(JwtToken);
    }

    @Test
    public void parseToken() throws Exception {
        byte[] accessSecret = this.accessSecret.getBytes(StandardCharsets.UTF_8);
        String jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtaW1uamFlMjI0NkBnbWFpbC5jb20iLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwidXNlcklkIjoxLCJpYXQiOjE2ODYwNTMwNTcsImV4cCI6MTY4NjA1NDg1N30.CuZ9yOMWAMth1wjRsp_p1pZN69s8zSt3XUCO4GJ_5qU";

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(accessSecret))
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
        System.out.println(claims.getSubject());
        System.out.println(claims.getIssuedAt());
        System.out.println(claims.getExpiration());
        System.out.println(claims.get("userId"));
        System.out.println(claims.get("roles"));

    }
}
