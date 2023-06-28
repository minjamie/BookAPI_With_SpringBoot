package com.example.bookAPI.repository;

import com.example.bookAPI.domain.MemberBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberBookRepository extends JpaRepository<MemberBook, Long> {
}
