package com.example.bookAPI.dto.member.login;

import lombok.Data;

@Data
public class LoginInfoDto {
    private Long memberId;
    private String email;
    private String name;
}
