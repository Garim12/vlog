// PostController.java
package com.sparta.vlog.controller;

import com.sparta.vlog.dto.DeleteResultDto;
import com.sparta.vlog.dto.PostRequestDto;
import com.sparta.vlog.dto.PostResponseDto;
import com.sparta.vlog.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
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
    public List<PostResponseDto> getAllPosts() {
        return postService.getPosts();
    }

    // 게시글 작성 API
    @PostMapping("/posts")
    public PostResponseDto createPost(@RequestBody PostRequestDto requestDto, HttpServletRequest request) {
        return postService.createPost(requestDto, request);
    }

    // 선택한 게시글 조회 API
    @GetMapping("/posts/{postId}")
    public PostResponseDto getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    // 선택한 게시글 수정 API
    @PutMapping("/posts/{postId}")
    public PostResponseDto updatePost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto, HttpServletRequest request) {
        return postService.updatePost(postId, requestDto, request);
    }

    // 선택한 게시글 삭제 API
    @DeleteMapping("/posts/{postId}")
    public DeleteResultDto deletePost(@PathVariable Long postId, HttpServletRequest request) {
        postService.deletePost(postId, request);
        return new DeleteResultDto("게시글 삭제 성공", HttpStatus.OK.value());
    }
}

