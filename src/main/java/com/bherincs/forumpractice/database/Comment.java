package com.bherincs.forumpractice.database;

import com.bherincs.forumpractice.controllers.dto.comment.CreateCommentDTO;
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
    private ForumUser owner;

    @OneToOne
    private Comment prevComment;

    @ManyToOne//Many Commnent to One BlogPost
    private BlogPost rootBlog;
    private boolean isDeleted;

    public Comment(String content, ForumUser owner, Comment prevComment, BlogPost rootBlog) {
        this.content = content;
        this.owner = owner;
        this.prevComment = prevComment;
        this.rootBlog = rootBlog;
        this.isDeleted = false;
    }

    public Comment() {
    }

    public Comment(Long id, String content, ForumUser owner, Comment prevComment, BlogPost rootBlog, boolean isDeleted) {
        this.id = id;
        this.content = content;
        this.owner = owner;
        this.prevComment = prevComment;
        this.rootBlog = rootBlog;
        this.isDeleted = isDeleted;
    }
}
