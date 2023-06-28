package com.example.bookAPI.service;

import com.example.bookAPI.domain.Book;
import com.example.bookAPI.domain.MemberBook;
import com.example.bookAPI.repository.MemberBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberBookService {

    final private MemberBookRepository memberBookRepository;

    @Transactional
    public void saveMemberBook(MemberBook memberBook) {
        memberBookRepository.save(memberBook);
    }
}

