package com.bherincs.forumpractice.service.inter;

import com.bherincs.forumpractice.controllers.dto.blog.BlogDTO;
import com.bherincs.forumpractice.controllers.dto.blog.DetailedBlogDTO;
import com.bherincs.forumpractice.service.dto.ServiceResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BlogService {
    Page<BlogDTO> findAllPosts(int pageNumber, int pageSize);
    Page<BlogDTO> findAllPostsWithTags(String tag, int pageNumber, int pageSize);
    ServiceResponse<BlogDTO> createBlogPost(String title, String content, String username, List<String> tags);
    ServiceResponse<DetailedBlogDTO> fetchPostById(Long id);
    ServiceResponse<DetailedBlogDTO> deleteBlogById(Long id, String username);
}
