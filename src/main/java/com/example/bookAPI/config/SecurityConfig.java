package com.example.bookAPI.config;

import com.example.bookAPI.security.jwt.exception.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsUtils;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configurable // Spring Security에 대한 설정
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationManagerConfig authenticationManagerConfig;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .csrf().disable()
                .cors()
                .and()
                .apply(authenticationManagerConfig)
                .and()
                .httpBasic().disable()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .mvcMatchers("api/signup", "api/login", "api/user/refresh").permitAll()
                .mvcMatchers(HttpMethod.GET, "/**").hasAnyRole("USER", "MANAGER", "ADMIN")
                .mvcMatchers(HttpMethod.POST, "/**").hasAnyRole("USER", "MANAGER", "ADMIN")
                .anyRequest().authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .and()
                .build();
    }

    // 암호를 암호화, 사용자 입력한 암호가 기존 암호화 일치하는 지 검사시 해당 Bean 사용
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
