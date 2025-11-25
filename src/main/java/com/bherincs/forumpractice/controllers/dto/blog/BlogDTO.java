package com.bherincs.forumpractice.controllers.dto.blog;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BlogDTO {
    private Long id;
    private String title;
    private String content;
    private String owner;
    private Date creationDate;
    private List<String> tags;

    public BlogDTO(Long id, String title, String content, String owner, Date creationDate, List<String> tags) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.owner = owner;
        this.creationDate = creationDate;
        this.tags = tags;
    }
}
