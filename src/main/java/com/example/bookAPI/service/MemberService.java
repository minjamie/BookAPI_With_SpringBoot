package com.example.bookAPI.service;

import com.example.bookAPI.domain.Member;
import com.example.bookAPI.domain.Role;
import com.example.bookAPI.repository.RoleRepository;
import com.example.bookAPI.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public Member addMember(Member member) {
        Optional<Role> memberRole = roleRepository.findByName("ROLE_USER");
        member.addRole(memberRole.get());
        Member saveMember = memberRepository.save(member);
        return saveMember;
    }

    @Transactional(readOnly = true)
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("not found member"));
    }
    @Transactional(readOnly = true)
    public Optional<Member> getMember(Long memberId){
        return memberRepository.findById(memberId);
    }
}
