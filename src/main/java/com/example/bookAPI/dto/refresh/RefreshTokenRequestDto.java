package com.example.bookAPI.dto.refresh;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RefreshTokenRequestDto {
    @NotEmpty
    String refreshToken;
}