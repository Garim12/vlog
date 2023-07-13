package com.sparta.vlog.service;

import com.sparta.vlog.entity.Comment;
import com.sparta.vlog.entity.Likes;
import com.sparta.vlog.entity.Post;
import com.sparta.vlog.entity.User;
import com.sparta.vlog.exception.NotFoundException;
import com.sparta.vlog.repository.CommentRepository;
import com.sparta.vlog.repository.LikesRepository;
import com.sparta.vlog.repository.PostRepository;
import com.sparta.vlog.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class LikesService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikesRepository likesRepository;
    private final MessageSource messageSource;

    public void likesClick(Long id, boolean requestDto, UserDetailsImpl userDetails) {
        Post post = findPost(id);
        User user = userDetails.getUser();
        Comment comment = findComment(id);

        boolean hasLikedPost = likesRepository.existsByPostAndUserAndComment(post, user, null);
        boolean hasLikedComment = likesRepository.existsByPostAndUserAndComment(null, user, comment);

        if (requestDto) {
            if (!hasLikedPost && comment == null) {
                Likes likes = new Likes(user, post);
                likesRepository.save(likes);
            } else if (!hasLikedComment && post == null) {
                Likes likes = new Likes(user, comment);
                likesRepository.save(likes);
            }
        } else {
            if (hasLikedPost && comment == null) {
                List<Likes> existingLikes = likesRepository.findByUserAndPost(user, post);
                for (Likes like : existingLikes) {
                    likesRepository.delete(like);
                }
            } else if (hasLikedComment && post == null) {
                List<Likes> existingLikes = likesRepository.findByUserAndComment(user, comment);
                for (Likes like : existingLikes) {
                    likesRepository.delete(like);
                }
            }
        }
    }

    private Post findPost(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage(
                        "not.found.Post",
                        null,
                        "Not Found Post",
                        Locale.getDefault()
                )
        ));
    }

    private Comment findComment(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(messageSource.getMessage(
                        "not.found.Comment",
                        null,
                        "Not Found Comment",
                        Locale.getDefault()
                )
                ));
    }

    public boolean hasLiked(Long id, UserDetailsImpl userDetails) {
        Post post = findPost(id);
        User user = userDetails.getUser();
        Comment comment = findComment(id);

        return likesRepository.existsByPostAndUserAndComment(post, user, comment);
    }

    public int getLikesCount(Long id) {
        Post post = findPost(id);
        Comment comment = findComment(id);

        int postLikesCount = likesRepository.countByPost(post);
        int commentLikesCount = likesRepository.countByComment(comment);

        return postLikesCount + commentLikesCount;
    }
}

