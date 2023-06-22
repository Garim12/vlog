package com.sparta.vlog.dto;

import com.sparta.vlog.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {
    private long id;
    private String title;
    private String content;
    private String username;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public PostResponseDto(Post post) {
            this.id = post.getId();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.username = post.getUsername();
            this.createdAt = post.getCreatedAt();
            this.modifiedAt = post.getModifiedAt();

    }
}
