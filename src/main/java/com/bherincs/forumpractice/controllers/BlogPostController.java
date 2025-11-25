package com.bherincs.forumpractice.controllers;

import com.azure.security.keyvault.jca.implementation.shaded.org.apache.hc.core5.http.NotImplementedException;
import com.bherincs.forumpractice.controllers.dto.blog.BlogDTO;
import com.bherincs.forumpractice.controllers.dto.blog.CreateBlogDTO;
import com.bherincs.forumpractice.controllers.dto.blog.DetailedBlogDTO;
import com.bherincs.forumpractice.service.JwtService;
import com.bherincs.forumpractice.service.inter.BlogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


//TODO: Add a more detailed return body, that can provide with some info about the error
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
    public ResponseEntity<BlogDTO> createBlogPost(HttpServletRequest request, @RequestBody CreateBlogDTO body){
        String token = request.getHeader("Authorization").substring(7);
        String username = jwtService.extractUsername(token);

        var createdBlogPost = blogService.createBlogPost(body.getTitle(), body.getContent(), username, body.getTags());

        if(createdBlogPost.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(createdBlogPost.get());
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
    public ResponseEntity<DetailedBlogDTO> fetchBlogById(@PathVariable Long id) {
        var result = blogService.fetchPostById(id);

        if(result.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(result.get());
    }
}
