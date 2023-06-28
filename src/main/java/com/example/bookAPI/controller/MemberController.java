package com.example.bookAPI.controller;

import com.example.bookAPI.domain.*;
import com.example.bookAPI.dto.member.login.MemberLoginRequestDto;
import com.example.bookAPI.dto.member.login.MemberLoginResponseDto;
import com.example.bookAPI.dto.member.signup.MemberSignupRequestDto;
import com.example.bookAPI.dto.member.signup.MemberSignupResponseDto;
import com.example.bookAPI.dto.memberBook.MemberBookRequestDto;
import com.example.bookAPI.dto.refresh.RefreshTokenRequestDto;
import com.example.bookAPI.security.jwt.util.JwtTokenizer;
import com.example.bookAPI.service.BookService;
import com.example.bookAPI.service.MemberService;
import com.example.bookAPI.service.RefreshTokenService;
import com.example.bookAPI.service.MemberBookService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Api(tags = "MemberApiController", description = "멤버 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Validated
public class MemberController {

    private final MemberService memberService;
    private final MemberBookService memberBookService;
    private final BookService bookService;
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenizer jwtTokenizer;
    private final PasswordEncoder passwordEncoder;

    @Operation(summary = "이메일로 멤버 찾기", description = "이메일로 존재하는 멤버 찾기")
    @GetMapping("/{email}")
    public Member findMember(@Parameter(description = "이메일", required = true, example = "minjae2246@gmail.com") @RequestParam String email){
        return memberService.findByEmail(email);
    }

    @Operation(summary = "회원가입", description = "이메일, 이름, 비밀번호로 가입하기")
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

        if(!memberSignupRequestDto.getMemberBooks().isEmpty()){
            for (MemberBookRequestDto memberBookRequestDto : memberSignupRequestDto.getMemberBooks()) {
                MemberBook memberBook = new MemberBook();

                memberBook.setMember(saveMember);
                memberBook.setHas(memberBookRequestDto.isHas());
                memberBook.setRead(memberBookRequestDto.isRead());

                Optional<Book> book = bookService.getBook(memberBookRequestDto.getBookId());
                memberBook.setBook(book.get());
                memberBookService.saveMemberBook(memberBook);
            }
        }

        MemberSignupResponseDto memberSignupResponseDto = new MemberSignupResponseDto();
        memberSignupResponseDto.setMemberId(saveMember.getMemberId());
        memberSignupResponseDto.setName(saveMember.getName());
        memberSignupResponseDto.setCreateDateTime(saveMember.getUpdateDateTime());
        memberSignupResponseDto.setEmail(saveMember.getEmail());


        return new ResponseEntity(memberSignupResponseDto, HttpStatus.CREATED);
    }

    @Operation(summary = "로그인", description = "이메일, 비밀번호로 로그인하기")
    @PostMapping("/login")
    public ResponseEntity login (@RequestBody @Valid MemberLoginRequestDto memberLoginRequestDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        Member member = memberService.findByEmail(memberLoginRequestDto.getEmail());
        if(!passwordEncoder.matches(memberLoginRequestDto.getPassword(), member.getPassword())){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        List<String> roles = member.getRoles().stream().map(Role::getName).collect(Collectors.toList());

        // jwt token created - jwt 라이브러리 이용하여 생성
        String accessToken = jwtTokenizer.createAccessToken(member.getMemberId(), member.getEmail(), member.getName(), roles);
        String refreshToken = jwtTokenizer.createRefreshToken(member.getMemberId(), member.getEmail(), member.getName(), roles);

        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setValue(refreshToken);
        refreshTokenEntity.setMemberId(member.getMemberId());
        refreshTokenService.addRefreshToken(refreshTokenEntity);

        MemberLoginResponseDto memberLoginResponseDto = MemberLoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .memberId(member.getMemberId())
                .name(member.getName())
                .build();

        return new ResponseEntity(memberLoginResponseDto, HttpStatus.OK);
    }

    @Operation(summary = "로그아웃", description = "리프레시 토큰으로 로그아웃 하기")
    @DeleteMapping("/logout")
    public ResponseEntity logout(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto){
        refreshTokenService.deleteRefreshToken(refreshTokenRequestDto.getRefreshToken());
        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "접근 토큰 재발급", description = "리프레시 토큰으로 접근 토큰 연장하기")
    @PostMapping("/refreshToken")
    public ResponseEntity refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDto){
        RefreshToken refreshToken = refreshTokenService.findRefreshToken(refreshTokenRequestDto.getRefreshToken()).orElseThrow(() -> new IllegalArgumentException("RefreshToken not Found"));
        Claims claims = jwtTokenizer.parseRefreshToken(refreshToken.getValue());

        Long memberId = Long.valueOf((Integer)claims.get("memberId"));
        Member member = memberService.getMember(memberId).orElseThrow(() -> new IllegalArgumentException("Member  not Found"));

        List roles = (List) claims.get("roles");
        String email = claims.getSubject();

        String accessToken = jwtTokenizer.createAccessToken(memberId, email, member.getName(), roles);

        MemberLoginResponseDto memberLoginResponseDto = MemberLoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getValue())
                .memberId(memberId)
                .name(member.getName())
                .build();

        return new ResponseEntity(memberLoginResponseDto, HttpStatus.OK);
    }
}
