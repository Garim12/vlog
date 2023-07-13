package com.sparta.vlog.service;

import com.sparta.vlog.entity.Comment;
import com.sparta.vlog.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    private CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment createComment(String content) {
        Comment comment = new Comment(content);
        return commentRepository.save(comment);
    }

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    public Optional<Comment> getCommentsByPost(Long postId) {
        return commentRepository.findById(postId);
    }

    public Comment updateComment(Long id, String newContent) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            comment.setContent(newContent);
            return commentRepository.save(comment);
        }
        return null;
    }

    public boolean deleteComment(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            commentRepository.delete(optionalComment.get());
            return true;
        }
        return false;
    }
}

