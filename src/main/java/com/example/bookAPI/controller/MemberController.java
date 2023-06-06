package com.example.bookAPI.controller;

import com.example.bookAPI.domain.Member;
import com.example.bookAPI.dto.member.login.MemberLoginRequestDto;
import com.example.bookAPI.dto.member.login.MemberLoginResponseDto;
import com.example.bookAPI.dto.member.signup.MemberSignupRequestDto;
import com.example.bookAPI.dto.member.signup.MemberSignupResponseDto;
import com.example.bookAPI.security.jwt.util.JwtTokenizer;
import com.example.bookAPI.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final JwtTokenizer jwtTokenizer;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody @Valid MemberSignupRequestDto memberSignupRequestDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Member member = new Member();
        member.setName(memberSignupRequestDto.getName());
        member.setEmail(memberSignupRequestDto.getEmail());
        member.setPassword(passwordEncoder.encode(memberSignupRequestDto.getPassword()));

        Member saveMember = memberService.addMember(member);

        MemberSignupResponseDto memberSignupResponseDto = new MemberSignupResponseDto();
        memberSignupResponseDto.setMemberId(saveMember.getMemberId());
        memberSignupResponseDto.setName(saveMember.getName());
        memberSignupResponseDto.setCreateDateTime(saveMember.getUpdateDateTime());
        memberSignupResponseDto.setEmail(saveMember.getEmail());

        return new ResponseEntity(memberSignupResponseDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity login (@RequestBody @Valid MemberLoginRequestDto loginDto){
        // email에 해당하는 사용자 정보 읽어와서 암호가 맞는지 검사하는 코드 존재

        Long userId = 1L;
        String email = loginDto.getEmail();
        List<String> roles = List.of("ROLE_USER");

        // jwt token created - jwt 라이브러리 이용하여 생성
        String accessToken = jwtTokenizer.createAccessToken(userId, email, roles);
        String refreshToken = jwtTokenizer.createRefreshToken(userId, email, roles);

        MemberLoginResponseDto memberLoginResponseDto = MemberLoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(userId)
                .name("야호")
                .build();
        return new ResponseEntity(memberLoginResponseDto, HttpStatus.OK);
    }
}
