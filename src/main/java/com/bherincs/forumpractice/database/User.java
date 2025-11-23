package com.bherincs.forumpractice.database;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String username;

    @Column(nullable = false, length = Integer.MAX_VALUE)
    @Nationalized//This, alongside of "length = Integer.MAX_VALUE" should set nvarchar(max)
    private String password;

    @OneToMany(mappedBy = "owner")
    private List<BlogPost> posts;

    @OneToMany(mappedBy = "owner")
    private List<Comment> comments;
}
