package com.bherincs.forumpractice.controllers.dto.blog;

import com.bherincs.forumpractice.controllers.dto.user.UserDTO;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private UserDTO owner;
    private String content;
    private CommentDTO parentComment;
}
