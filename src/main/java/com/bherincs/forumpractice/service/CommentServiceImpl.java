package com.bherincs.forumpractice.service;

import com.bherincs.forumpractice.controllers.dto.comment.CommentDTO;
import com.bherincs.forumpractice.controllers.dto.comment.CreateCommentDTO;
import com.bherincs.forumpractice.database.Comment;
import com.bherincs.forumpractice.mapper.CommentMapper;
import com.bherincs.forumpractice.repository.BlogRepository;
import com.bherincs.forumpractice.repository.CommentRepository;
import com.bherincs.forumpractice.repository.UserRepository;
import com.bherincs.forumpractice.service.dto.ServiceResponse;
import com.bherincs.forumpractice.service.inter.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper mapper;

    public CommentServiceImpl(UserRepository userRepository, BlogRepository blogRepository, CommentRepository commentRepository, CommentMapper mapper) {
        this.userRepository = userRepository;
        this.blogRepository = blogRepository;
        this.commentRepository = commentRepository;
        this.mapper = mapper;
    }

    @Override
    public ServiceResponse<CommentDTO> createComment(String username, CreateCommentDTO dto) {
        var userResult = userRepository.findByUsername(username);

        if(userResult.isEmpty()){
            log.warn("Could not find user for creating comment: {}", username);
            return new ServiceResponse<>(null, "Could not find user!");
        }

        var blogResult = blogRepository.findById(dto.getBlogId());

        if(blogResult.isEmpty()){
            log.warn("Given blog could not be found by id: {}", dto.getBlogId());
            return new ServiceResponse<>(null, "Given blog could not be found!");
        }

        Comment prevComment = null;
        if(dto.getPrevCommentId() != null){
            var commentResult = commentRepository.findById(dto.getPrevCommentId());

            if(commentResult.isEmpty()){
                log.warn("Given prev comment could not be found by id: {}", dto.getPrevCommentId());
                return new ServiceResponse<>(null, "Previous comment given, but could not be found!");
            }

            prevComment = commentResult.get();
        }

        try{
            var newComment = new Comment(dto.getContent(), userResult.get(), prevComment, blogResult.get());
            commentRepository.save(newComment);

            log.info("New comment posted by: {}", username);

            return new ServiceResponse<>(mapper.toDTO(newComment), null);
        }
        catch (Exception ex){
            log.error("There was an unexpected error while saving comment for blogpost: {} and prev comment: {}. Exception: {}", dto.getBlogId(), dto.getPrevCommentId(), ex.getMessage());
            return new ServiceResponse<>(null, "There was unexpected error while saving the comment");
        }
    }

    @Override
    public ServiceResponse<CommentDTO> deleteComment(String username, Long id) {
        var userResult = userRepository.findByUsername(username);

        if(userResult.isEmpty()){
            log.warn("Could not find user for creating comment: {}", username);
            return new ServiceResponse<>(null, "Could not find user!");
        }

        var commentResult = commentRepository.findById(id);

        if(commentResult.isEmpty()){
            log.warn("Given comment comment could not be found by id: {}", id);
            return new ServiceResponse<>(null, "Comment given, but could not be found!");
        }

        var comment = commentResult.get();
        var user = userResult.get();

        if(comment.getOwner() != user){
            if(!user.getRoles().contains("ROLE_ADMIN")){
                log.warn("The user tried to delete a comment that he/she is not authorized to delete!. User: {}, comment: {}", username, id);
                return new ServiceResponse<>(null, "You are not authorized!");
            }
        }

        comment.setDeleted(true);
        log.info("Comment: {} deleted by user: {}", id, username);
        return new ServiceResponse<>(mapper.toDTO(comment), null);
    }
}
