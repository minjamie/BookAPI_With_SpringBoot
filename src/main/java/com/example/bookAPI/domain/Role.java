package com.example.bookAPI.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="role")
@NoArgsConstructor
@Setter
@Getter
public class Role {
    @Id
    @Column(name="role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;


    @Column(length = 20)
    private String name;

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", name='" + name + '\'' +
                '}';
    }
}

