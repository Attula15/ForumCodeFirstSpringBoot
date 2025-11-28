package com.bherincs.forumpractice.controllers;

import com.azure.security.keyvault.jca.implementation.shaded.org.apache.hc.core5.http.NotImplementedException;
import com.bherincs.forumpractice.controllers.dto.ApiResponseDTO;
import com.bherincs.forumpractice.controllers.dto.blog.BlogDTO;
import com.bherincs.forumpractice.controllers.dto.blog.CreateBlogDTO;
import com.bherincs.forumpractice.controllers.dto.blog.DetailedBlogDTO;
import com.bherincs.forumpractice.service.JwtService;
import com.bherincs.forumpractice.service.inter.BlogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Slf4j
@RestController
@RequestMapping("/api/blog")
public class BlogPostController {

    private final JwtService jwtService;
    private final BlogService blogService;

    public BlogPostController(JwtService jwtService, BlogService blogService) {
        this.jwtService = jwtService;
        this.blogService = blogService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDTO<BlogDTO>> createBlogPost(HttpServletRequest request, @RequestBody CreateBlogDTO body){
        String token = request.getHeader("Authorization").substring(7);
        String username = jwtService.extractUsername(token);

        ApiResponseDTO<BlogDTO> response;

        try{
            var createdBlogPost = blogService.createBlogPost(body.getTitle(), body.getContent(), username, body.getTags());

            response = new ApiResponseDTO<>(createdBlogPost.getData(), createdBlogPost.getErrorMessage());

            if(!response.isSuccess()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        catch (Exception ex) {
            log.error("There was an unexpected error while creating post: {}", ex.getMessage());
            response = new ApiResponseDTO<>(null, "There was an unexpected error while creating the post");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/posts")
    public ResponseEntity<Page<BlogDTO>> fetchBlogPosts(@RequestParam int pageSize, @RequestParam int pageNumber) {
        if(pageSize < 1 || pageNumber < 0){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        var result = blogService.findAllPosts(pageNumber, pageSize);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<ApiResponseDTO<DetailedBlogDTO>> fetchBlogById(@PathVariable Long id) {
        var result = blogService.fetchPostById(id);

        var response = new ApiResponseDTO<DetailedBlogDTO>(result.getData(), result.getErrorMessage());

        if(!response.isSuccess()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<ApiResponseDTO<DetailedBlogDTO>> deleteBlogById(HttpServletRequest request, @PathVariable Long id){
        String token = request.getHeader("Authorization").substring(7);
        String username = jwtService.extractUsername(token);

        var result = blogService.deleteBlogById(id, username);

        var response = new ApiResponseDTO<DetailedBlogDTO>(result.getData(), result.getErrorMessage());

        if(!response.isSuccess()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
