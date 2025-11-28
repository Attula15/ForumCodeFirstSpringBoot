package com.bherincs.forumpractice.mapper;

import com.bherincs.forumpractice.controllers.dto.comment.CommentDTO;
import com.bherincs.forumpractice.database.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(source = "prevComment", target = "parentComment", qualifiedByName = "parentCommentMapper")
    CommentDTO toDTO(Comment entity);

    @Named("parentCommentMapper")
    default Long parseParentComment(Comment parentComment){
        if(parentComment != null){
            return parentComment.getId();
        }
        return null;
    }
}
