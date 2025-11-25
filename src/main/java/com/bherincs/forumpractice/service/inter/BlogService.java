package com.bherincs.forumpractice.service.inter;

import com.bherincs.forumpractice.controllers.dto.blog.BlogDTO;
import com.bherincs.forumpractice.controllers.dto.blog.DetailedBlogDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BlogService {
    Page<BlogDTO> findAllPosts(int pageNumber, int pageSize);
    Page<BlogDTO> findAllPostsWithTags(String tag, int pageNumber, int pageSize);
    Optional<BlogDTO> createBlogPost(String title, String content, String username, List<String> tags);
    Optional<DetailedBlogDTO> fetchPostById(Long id);
}
