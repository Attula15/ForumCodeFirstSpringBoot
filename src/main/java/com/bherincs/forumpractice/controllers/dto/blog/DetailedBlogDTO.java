package com.bherincs.forumpractice.controllers.dto.blog;

import com.bherincs.forumpractice.controllers.dto.comment.CommentDTO;
import com.bherincs.forumpractice.controllers.dto.user.UserDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DetailedBlogDTO {
    private final Long id;
    private final String title;
    private final String content;
    private final UserDTO owner;
    private final List<String> tags;
    private final Date creationDate;
    private final List<CommentDTO> comments;
}
