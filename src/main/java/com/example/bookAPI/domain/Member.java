package com.example.bookAPI.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="member")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(length = 255)
    private String email;

    @Column(length = 50)
    private String name;

    @Column(length = 500)
    private String password;

    @CreationTimestamp
    private LocalDateTime createDateTime;

    @CreationTimestamp
    private LocalDateTime updateDateTime;

    @Column(name = "activated")
    private boolean activated;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "member_role",
            joinColumns = @JoinColumn(name = "member_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    Set<Role> roles = new HashSet<>();

    @Override
    public String toString() {
        return "Member{" +
                "memberId=" + memberId +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", createDateTime=" + createDateTime +
                ", updateDateTime=" + updateDateTime +
                ", activated=" + activated +
                '}';
    }

    public void addRole(Role role){
        roles.add(role);
    }
}

