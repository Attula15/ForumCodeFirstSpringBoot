package com.bherincs.forumpractice.controllers;

import com.azure.security.keyvault.jca.implementation.shaded.org.apache.hc.core5.http.NotImplementedException;
import com.bherincs.forumpractice.controllers.dto.blog.BlogDTO;
import com.bherincs.forumpractice.controllers.dto.blog.CreateBlogDTO;
import com.bherincs.forumpractice.service.JwtService;
import com.bherincs.forumpractice.service.inter.BlogService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<BlogDTO>> fetchBlogPosts(@RequestParam Long pageSize, @RequestParam Long pageNumber) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<BlogDTO> fetchBlogById(@PathVariable Long id) throws NotImplementedException {
        throw new NotImplementedException();
    }
}
