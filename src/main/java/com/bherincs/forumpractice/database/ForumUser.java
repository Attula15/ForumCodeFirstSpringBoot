package com.bherincs.forumpractice.database;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.util.List;

@Entity
@Table(name = "users")
@Data
public class ForumUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String username;

    @Column(length = Integer.MAX_VALUE)
    @Nationalized
    private String roles;

    @Column(nullable = false, length = Integer.MAX_VALUE)
    @Nationalized//This, alongside of "length = Integer.MAX_VALUE" should set nvarchar(max) or text
    private String password;

    @OneToMany(mappedBy = "owner")
    private List<BlogPost> posts;

    @OneToMany(mappedBy = "owner")
    private List<Comment> comments;

    public ForumUser() {
    }

    public ForumUser(String password, String username) {
        this.password = password;
        this.username = username;
    }
}
