package com.example.bookAPI.dto.member.signup;

import lombok.*;

import java.time.LocalDateTime;

@Data
public class MemberSignupResponseDto {
    private Long memberId;
    private String email;
    private String name;
    private LocalDateTime createDateTime;
}
