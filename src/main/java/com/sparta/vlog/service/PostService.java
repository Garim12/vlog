// PostService.java
package com.sparta.vlog.service;

import com.sparta.vlog.dto.PostDto;
import com.sparta.vlog.entity.Post;
import com.sparta.vlog.repository.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> getPosts() {
        return postRepository.findAllByOrderByDateDesc();
    }

    public Post createPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setUsername(postDto.getUsername());
        post.setPassword(postDto.getPassword());
        post.setContent(postDto.getContent());
        post.setDate(LocalDateTime.now());
        return postRepository.save(post);
    }

    public ResponseEntity<Post> getPostById(Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            return ResponseEntity.ok(post);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Post> updatePost(Long postId, PostDto updatedPostDto) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (post.getPassword().equals(updatedPostDto.getPassword())) {
                post.setTitle(updatedPostDto.getTitle());
                post.setUsername(updatedPostDto.getUsername() != null ? updatedPostDto.getUsername() : post.getUsername());
                post.setContent(updatedPostDto.getContent());
                postRepository.save(post);
                return ResponseEntity.ok(post);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<String> deletePost(Long postId, String password) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (post.getPassword().equals(password)) {
                postRepository.delete(post);
                return ResponseEntity.ok("게시글이 성공적으로 삭제되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
