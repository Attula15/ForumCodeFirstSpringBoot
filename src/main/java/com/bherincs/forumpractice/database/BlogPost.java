package com.bherincs.forumpractice.database;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.util.List;

@Entity
@Table(name = "blog_posts")
@Data
public class BlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, length = Integer.MAX_VALUE)
    @Nationalized//This, alongside of "length = Integer.MAX_VALUE" should set nvarchar(max)
    private String content;

    @ManyToOne//Many BlogPost to One User
    private User owner;

    @ManyToMany
    @JoinTable(
            name = "tag_blog_connection_table",
            joinColumns = @JoinColumn(name = "blog_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;

    @OneToMany(mappedBy = "rootBlog")//One BlogPost to Many Comment
    private List<Comment> comments;
}
