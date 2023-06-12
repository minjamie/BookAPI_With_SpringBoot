package com.example.bookAPI.security.jwt.provider;

import com.example.bookAPI.dto.member.login.LoginInfoDto;
import com.example.bookAPI.security.jwt.token.JwtAuthenticationToken;
import com.example.bookAPI.security.jwt.util.JwtTokenizer;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtTokenizer jwtTokenizer;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) authentication;

        Claims claims = jwtTokenizer.parseAccessToken(authenticationToken.getToken());
        String email = claims.getSubject();
        Long memberId = claims.get("memberId", Long.class);
        String name = claims.get("name", String.class);
        List<GrantedAuthority> authorities = getGrantedAuthorities(claims);

        LoginInfoDto loginInfo = new LoginInfoDto();
        loginInfo.setMemberId(memberId);
        loginInfo.setEmail(email);
        loginInfo.setName(name);

        return new JwtAuthenticationToken(authorities, loginInfo, null);
    }

    private List<GrantedAuthority> getGrantedAuthorities(Claims claims) {
        List<String> roles = (List<String>) claims.get("roles");
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(()-> role);
        }
        return authorities;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}