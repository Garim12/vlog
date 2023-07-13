package com.sparta.vlog.repository;

import com.sparta.vlog.entity.Comment;
import com.sparta.vlog.entity.Likes;
import com.sparta.vlog.entity.Post;
import com.sparta.vlog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    List<Likes> findByUserAndPost(User user, Post post);
    List<Likes> findByUserAndComment(User user, Comment comment);
    int countByPost(Post post);
    int countByComment(Comment comment);
    boolean existsByPostAndUserAndComment(Post post, User user, Comment comment);
}

