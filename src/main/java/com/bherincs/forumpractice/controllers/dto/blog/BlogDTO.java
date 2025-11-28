package com.bherincs.forumpractice.controllers.dto.blog;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BlogDTO {
    private final Long id;
    private final String title;
    private final String content;
    private final String owner;
    private final Date creationDate;
    private final List<String> tags;

    public BlogDTO(Long id, String title, String content, String owner, Date creationDate, List<String> tags) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.owner = owner;
        this.creationDate = creationDate;
        this.tags = tags;
    }
}
