package com.bherincs.forumpractice.controllers.dto.comment;

import lombok.Data;

@Data
public class CreateCommentDTO {
    private final String content;
    private final Long blogId;
    private final Long prevCommentId;
}
