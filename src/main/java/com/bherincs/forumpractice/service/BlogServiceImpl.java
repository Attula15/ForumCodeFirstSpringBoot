package com.bherincs.forumpractice.service;

import com.bherincs.forumpractice.controllers.BlogPostController;
import com.bherincs.forumpractice.controllers.dto.blog.BlogDTO;
import com.bherincs.forumpractice.database.BlogPost;
import com.bherincs.forumpractice.database.ForumUser;
import com.bherincs.forumpractice.database.Tag;
import com.bherincs.forumpractice.mapper.entityMapper;
import com.bherincs.forumpractice.repository.BlogRepository;
import com.bherincs.forumpractice.repository.TagRepository;
import com.bherincs.forumpractice.repository.UserRepository;
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

        return blogRepository.findAll(pageable).map(mapper::toBlogDTO);//This is going to be interesting, how is it going to know to fetch the owner name from the owner entity?
    }

    @Override
    public Page<BlogDTO> findAllPostsWithTags(String tag, int pageNumber, int pageSize) {
        return null;
    }

    @Override
    @Transactional
    public Optional<BlogDTO> createBlogPost(String title, String content, String username, List<String> tags) {
        List<Tag> listOfTagsFound = tagRepository.findTagsByNames(tags);
        Optional<ForumUser> foundUser = userRepository.findByUsername(username);

        List<String> diffOfTags = new ArrayList<>(tags);
        diffOfTags.removeAll(listOfTagsFound.stream().map(Tag::getName).toList());

        if(foundUser.isEmpty()){
            return Optional.empty();
        }

        List<Tag> newTags = new ArrayList<>();

        for(var newTagName : diffOfTags){
            Tag newTag = new Tag();
            newTag.setName(newTagName);
            newTags.add(newTag);
        }

        tagRepository.saveAll(newTags);
        listOfTagsFound.addAll(newTags);

        BlogPost newPost = new BlogPost(title, content, foundUser.get(), Date.from(Instant.now()), listOfTagsFound);
        blogRepository.save(newPost);

        return Optional.of(mapper.toBlogDTO(newPost));
    }
}
