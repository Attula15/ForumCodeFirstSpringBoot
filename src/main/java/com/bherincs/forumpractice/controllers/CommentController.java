package com.bherincs.forumpractice.controllers;

import com.bherincs.forumpractice.controllers.dto.ApiResponseDTO;
import com.bherincs.forumpractice.controllers.dto.comment.CommentDTO;
import com.bherincs.forumpractice.controllers.dto.comment.CreateCommentDTO;
import com.bherincs.forumpractice.controllers.helper.ControllerHelper;
import com.bherincs.forumpractice.service.JwtService;
import com.bherincs.forumpractice.service.inter.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;
    private final JwtService jwtService;

    public CommentController(CommentService commentService, JwtService jwtService) {
        this.commentService = commentService;
        this.jwtService = jwtService;
    }

    @PostMapping("/comment")
    public ResponseEntity<ApiResponseDTO<CommentDTO>> createComment(HttpServletRequest request, @RequestBody CreateCommentDTO body){
        String username = ControllerHelper.fetchUserNameFromToken(request, jwtService);

        var commentResult = commentService.createComment(username, body);

        if(!commentResult.isSuccess()){
            var response = new ApiResponseDTO<CommentDTO>(null, commentResult.getErrorMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.ok(new ApiResponseDTO<>(commentResult.getData(), null));
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<ApiResponseDTO<CommentDTO>> deleteComment(HttpServletRequest request, @PathVariable Long id){
        String username = ControllerHelper.fetchUserNameFromToken(request, jwtService);

        var commentDeleteResult = commentService.deleteComment(username, id);

        if(!commentDeleteResult.isSuccess()){
            var response = new ApiResponseDTO<CommentDTO>(null, commentDeleteResult.getErrorMessage());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.ok(new ApiResponseDTO<>(commentDeleteResult.getData(), null));
    }
}
