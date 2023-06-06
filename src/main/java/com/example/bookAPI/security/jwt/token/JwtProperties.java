package com.example.bookAPI.security.jwt.token;

public interface JwtProperties {
    String SECRET_KEY = "u8x/A%D*G-KaPdSgVkYp3s6v9y$B&E(H+MbQeThWmZq4t7w!z%C*F-J@NcRfUjXn";
    String REFRESH_KEY = "t6w9z$C&F)J@McQfTjWnZr4u7x!A%D*G-KaPdRgUkXp2s5v8y/B?E(H+MbQeThVm";
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
    long ACCESS_TOKEN_EXPIRATION_TIME = 30 * 60 * 1000L;
    long REFRESH_TOKEN_EXPIRATION_TIME = 7 * 24 * 60 * 60 * 1000L;
}
