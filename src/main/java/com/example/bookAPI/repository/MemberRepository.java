package com.example.bookAPI.repository;

import com.example.bookAPI.domain.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    @EntityGraph(attributePaths = "roles")
    Optional<Member> findOneWithRolesByEmail(String email);
}
