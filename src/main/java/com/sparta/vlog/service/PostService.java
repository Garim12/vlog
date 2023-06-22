package com.sparta.vlog.service;

import com.sparta.vlog.dto.PostRequestDto;
import com.sparta.vlog.dto.PostResponseDto;
import com.sparta.vlog.entity.Post;
import com.sparta.vlog.entity.User;
import com.sparta.vlog.jwt.JwtUtil;
import com.sparta.vlog.repository.PostRepository;
import com.sparta.vlog.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public PostResponseDto createPost(PostRequestDto requestDto, HttpServletRequest request) {
        User user = checkToken(request);

        if (user == null) {
            throw new IllegalArgumentException("인증된 사용자가 아닙니다.");
        }

        Post post = new Post(requestDto, user);
        postRepository.save(post);
        return new PostResponseDto(post);
    }

    @GetMapping("/api/posts")
    public List<PostResponseDto> getPosts() {
        List<Post> posts = postRepository.findAllByOrderByDateDesc();
        List<PostResponseDto> postResponseDto = new ArrayList<>();

        for (Post post : posts) {
            postResponseDto.add(new PostResponseDto(post));
        }
        return postResponseDto;
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("아이디가 일치하지 않습니다."));
        return new PostResponseDto(post);
    }

    public PostResponseDto updatePost(Long postId, @RequestBody PostRequestDto requestDto, HttpServletRequest request) {
        User user = checkToken(request);

        if (user == null) {
            throw new IllegalArgumentException("인증된 사용자가 아닙니다.");
        }

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("해당 글이 존재하지 않습니다.")
        );

        if (!post.getUser().equals(user)) {
            throw new IllegalArgumentException("글 작성자가 아닙니다.");
        }

        post.update(requestDto);
        return new PostResponseDto(post);
    }

    @Transactional
    public void deletePost(Long postId, HttpServletRequest request) {
       User user = checkToken(request);

       if (user == null) {
           throw new IllegalArgumentException("인증된 사용자가 아닙니다.");
       }

       Post post = postRepository.findById(postId).orElseThrow(
               () -> new IllegalStateException("해당 글이 존재하지 않습니다.")
       );

       if (post.getUser().equals(user)) {
           postRepository.delete(post);
       }
    }

    public User checkToken(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            return user;
        }
        return null;
    }
}
