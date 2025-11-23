package com.bherincs.forumpractice.database;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "comments")
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String content;

    @ManyToOne
    private User owner;

    @OneToOne
    private Comment prevComment;

    @ManyToOne//Many Commnent to One BlogPost
    private BlogPost rootBlog;
}
