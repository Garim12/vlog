// PostController.java
package com.sparta.vlog.controller;

import com.sparta.vlog.dto.PostDto;
import com.sparta.vlog.entity.Post;
import com.sparta.vlog.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 전체 게시글 목록 조회 API
    @GetMapping("/posts")
    public List<Post> getAllPosts() {
        return postService.getPosts();
    }

    // 게시글 작성 API
    @PostMapping("/posts")
    public Post createPost(@RequestBody PostDto postDto, HttpServletRequest request) {
        return postService.createPost(postDto, request);
    }

    // 선택한 게시글 조회 API
    @GetMapping("/posts/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    // 선택한 게시글 수정 API
    @PutMapping("/posts/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable Long postId, @RequestBody PostDto updatedPostDto, HttpServletRequest request) {
        return postService.updatePost(postId, updatedPostDto, request);
    }

    // 선택한 게시글 삭제 API
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId, @RequestParam String password, HttpServletRequest request) {
        return postService.deletePost(postId, password, request);
    }
}

