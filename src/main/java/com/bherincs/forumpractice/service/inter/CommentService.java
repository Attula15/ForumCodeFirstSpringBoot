package com.bherincs.forumpractice.service.inter;

import com.bherincs.forumpractice.controllers.dto.comment.CommentDTO;
import com.bherincs.forumpractice.controllers.dto.comment.CreateCommentDTO;
import com.bherincs.forumpractice.service.dto.ServiceResponse;

public interface CommentService {
    ServiceResponse<CommentDTO> createComment(String username, CreateCommentDTO dto);
    ServiceResponse<CommentDTO> deleteComment(Long id);
}
