package com.example.bookAPI.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="book")
@Getter
@Setter
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="book_id")
    private Long bookId;
    private String title;
    private String subTitle;
    private String writer;
    private String publisher;
    private String publishDate;
    @Lob
    @Column(nullable = true)
    private String introduce;
    private String img;
    @Column(name="detail_num")
    private String detailNum;
    private boolean isEbook;
    private int count;

    @ManyToOne
    @JoinColumn(name ="category_id")
    private Category category;

//    @OneToMany(mappedBy = "book")
//    private List<MemberBook> memberBooks = new ArrayList<>();

    @OneToMany(mappedBy="book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookReview> bookReviews = new ArrayList<>();
}
