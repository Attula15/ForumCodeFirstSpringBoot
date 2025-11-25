package com.bherincs.forumpractice.mapper;

import com.bherincs.forumpractice.controllers.dto.blog.BlogDTO;
import com.bherincs.forumpractice.controllers.dto.blog.CommentDTO;
import com.bherincs.forumpractice.controllers.dto.blog.DetailedBlogDTO;
import com.bherincs.forumpractice.controllers.dto.user.UserDTO;
import com.bherincs.forumpractice.database.BlogPost;
import com.bherincs.forumpractice.database.Comment;
import com.bherincs.forumpractice.database.ForumUser;
import com.bherincs.forumpractice.database.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface entityMapper {
    @Mapping(source = "owner", target = "owner", qualifiedByName = "mapOwnerToString")
    @Mapping(source = "tags", target = "tags", qualifiedByName = "mapTagsToListString")
    BlogDTO toBlogDTO(BlogPost entity);

    //@Mapping(source = "owner", target = "owner")
    @Mapping(source = "tags", target = "tags", qualifiedByName = "mapTagsToListString")
    DetailedBlogDTO toDTO(BlogPost entity);

    UserDTO toDTO(ForumUser entity);

    CommentDTO toDTO(Comment entity);

    @Named("mapOwnerToString")
    default String mapOwnerToString(ForumUser user){
        return user != null ? user.getUsername() : "";
    }

    @Named("mapTagsToListString")
    default List<String> mapTags(List<Tag> tags){
        List<String> listOfTags = tags.stream().map(Tag::getName).toList();
        return listOfTags;
    }
}
