package com.sparta.vlog.repository;

import com.sparta.vlog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // Comment 엔티티와 관련된 데이터베이스 작업을 처리하기 위한 메서드를 추가할 수 있습니다.
    Optional<Comment> findByPostId(Long postId);
}
