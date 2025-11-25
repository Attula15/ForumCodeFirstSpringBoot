package com.bherincs.forumpractice.mapper;

import com.bherincs.forumpractice.controllers.dto.blog.BlogDTO;
import com.bherincs.forumpractice.database.BlogPost;
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
