package com.bherincs.forumpractice.controllers.dto.blog;

import com.bherincs.forumpractice.controllers.dto.user.UserDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DetailedBlogDTO {
    private Long id;
    private String title;
    private String content;
    private UserDTO owner;
    private List<String> tags;
    private Date creationDate;
    private List<CommentDTO> comments;
}
