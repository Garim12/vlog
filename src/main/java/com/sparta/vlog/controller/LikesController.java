package com.sparta.vlog.controller;

import com.sparta.vlog.security.UserDetailsImpl;
import com.sparta.vlog.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class LikesController {
    private final LikesService likesService;

    @GetMapping("/getLikes/{id}")
    public boolean getLikes(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likesService.hasLiked(id, userDetails);
    }

    @GetMapping("/getLikesNumbers/{id}")
    public Map<String, Integer> getLikesNumbers(@PathVariable Long id) {
        return likesService.getLikesCount(id);
    }

    @DeleteMapping("/posts/{id}/LikesClick")
    public void postLikesDelete(@PathVariable Long id, @RequestParam boolean requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        likesService.likesClick(id, requestDto, userDetails);
    }

    @DeleteMapping("/comments/{id}/LikesClick")
    public void commentLikesDelete(@PathVariable Long id, @RequestParam boolean requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        likesService.likesClick(id, requestDto, userDetails);
    }
}
