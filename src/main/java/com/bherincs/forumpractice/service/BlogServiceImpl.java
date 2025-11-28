package com.bherincs.forumpractice.service;

import com.bherincs.forumpractice.controllers.dto.blog.BlogDTO;
import com.bherincs.forumpractice.controllers.dto.blog.DetailedBlogDTO;
import com.bherincs.forumpractice.database.BlogPost;
import com.bherincs.forumpractice.database.ForumUser;
import com.bherincs.forumpractice.database.Tag;
import com.bherincs.forumpractice.mapper.entityMapper;
import com.bherincs.forumpractice.repository.BlogRepository;
import com.bherincs.forumpractice.repository.TagRepository;
import com.bherincs.forumpractice.repository.UserRepository;
import com.bherincs.forumpractice.service.dto.ServiceResponse;
import com.bherincs.forumpractice.service.inter.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

@Slf4j
@Service
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final entityMapper mapper;

    public BlogServiceImpl(BlogRepository blogRepository, TagRepository tagRepository, UserRepository userRepository, entityMapper mapper) {
        this.blogRepository = blogRepository;
        this.tagRepository = tagRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public Page<BlogDTO> findAllPosts(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        return blogRepository.findAll(pageable).map(mapper::toBlogDTO);
    }

    @Override
    public Page<BlogDTO> findAllPostsWithTags(String tag, int pageNumber, int pageSize) {
        return null;
    }

    @Override
    @Transactional
    public ServiceResponse<BlogDTO> createBlogPost(String title, String content, String username, List<String> tags) {
        List<Tag> listOfTagsFound = tagRepository.findTagsByNames(tags);
        Optional<ForumUser> foundUser = userRepository.findByUsername(username);

        List<String> diffOfTags = new ArrayList<>(tags);
        diffOfTags.removeAll(listOfTagsFound.stream().map(Tag::getName).toList());

        if(foundUser.isEmpty()){
            return new ServiceResponse<>(null, "User not found");
        }

        List<Tag> newTags = new ArrayList<>();

        for(var newTagName : diffOfTags){
            Tag newTag = new Tag();
            newTag.setName(newTagName);
            newTags.add(newTag);
        }

        try{
            tagRepository.saveAll(newTags);
            listOfTagsFound.addAll(newTags);
        }
        catch (Exception ex){
            List<String> newTagNames = newTags.stream().map(Tag::getName).toList();

            log.error("An unexpected error occurred, while saving the new tags to the database, tags: {}, error: {}",
                    String.join(" ,", newTagNames), ex.getMessage());
            return new ServiceResponse<>(null, "An unexpected error occurred while saving the new tags to the database");
        }

        try{
            BlogPost newPost = new BlogPost(title, content, foundUser.get(), Date.from(Instant.now()), listOfTagsFound);
            blogRepository.save(newPost);

            return new ServiceResponse<>(mapper.toBlogDTO(newPost), null);
        }
        catch (Exception ex){
            log.error("An unexpected error occurred, while saving the post to the database: {}", ex.getMessage());
            return new ServiceResponse<>(null, "An unexpected error occurred while saving the post to the database");
        }
    }

    @Override
    public ServiceResponse<DetailedBlogDTO> fetchPostById(Long id) {
        var entity = blogRepository.findById(id);

        if(entity.isEmpty()){
            log.warn("Could not find post with id: {}", id);
            return new ServiceResponse<>(null, String.format("Could not find post with id: %s", id));
        }

        return new ServiceResponse<>(mapper.toDTO(entity.get()), null);
    }

    @Override
    public ServiceResponse<DetailedBlogDTO> deleteBlogById(Long id, String username) {
        var entityResult = blogRepository.findById(id);

        if(entityResult.isEmpty()){
            log.warn("Could not find post with id: {}", id);
            return new ServiceResponse<>(null, String.format("Could not find post with id: %s", id));
        }

        var entity = entityResult.get();

        if(!entity.getOwner().getUsername().equals(username)){
            var userResult = userRepository.findByUsername(username);
            if(!(userResult.isPresent() && userResult.get().getRoles().contains("ROLE_ADMIN"))){
                log.warn("User tried to delete a a post that doesn't belong to him/her. User: {}, blogId: {}", username, id);
                return  new ServiceResponse<>(null, String.format("You don't have the permission to delete this post."));
            }
            log.info("Admin tries to delete a post");
        }

        DetailedBlogDTO dto = mapper.toDTO(entity);

        try{
            blogRepository.delete(entity);
        }
        catch (Exception ex){
            log.error("An unexpected error occurred, when deleting post with id: {}. Exception: {}", id, ex.getMessage());
            return  new ServiceResponse<>(null, String.format("An unexpected error occurred, please try again later"));
        }

        return new ServiceResponse<>(dto, null);
    }
}
