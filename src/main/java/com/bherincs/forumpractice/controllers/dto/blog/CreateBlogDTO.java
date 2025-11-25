package com.bherincs.forumpractice.controllers.dto.blog;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CreateBlogDTO {
    private String title;
    private String content;
    private List<String> tags;
}
