package com.bherincs.forumpractice.controllers.dto.blog;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CreateBlogDTO {
    private final String title;
    private final String content;
    private final List<String> tags;
}
