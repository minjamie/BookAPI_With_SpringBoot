package com.example.bookAPI.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
    private String subtitle;
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
    private LocalDateTime createDateTime;
    @CreationTimestamp
    private LocalDateTime updateDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="category_id")
    private Category category;

    @OneToMany(mappedBy = "book")
    @JsonIgnore
    private List<MemberBook> memberBooks = new ArrayList<>();


    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy="book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookReview> bookReviews = new ArrayList<>();
}
