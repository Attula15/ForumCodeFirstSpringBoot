package com.bherincs.forumpractice.controllers.dto.comment;

import com.bherincs.forumpractice.controllers.dto.user.UserDTO;
import lombok.Data;

@Data
public class CommentDTO {
    private final Long id;
    private final UserDTO owner;
    private final String content;
    private final Long parentComment;
}
