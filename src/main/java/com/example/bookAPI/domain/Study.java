package com.example.bookAPI.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "study")
@Getter
@Setter
@NoArgsConstructor
public class Study {

    @Id
    @Column(name="study_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studyId;

    private String title;
    private String description;

    @OneToMany(mappedBy = "study")
    private List<Post> studyPosts = new ArrayList<>();
}
