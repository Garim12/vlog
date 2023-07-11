package com.sparta.vlog.controller;

import com.sparta.vlog.security.UserDetailsImpl;
import com.sparta.vlog.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    @GetMapping("/getLikes/{id}")
    public boolean getLikes(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likesService.getLikes(id, userDetails);
    }

    @GetMapping("/getLikes/{id}")
    public int getLikesNumbers(@PathVariable Long id) []
        return likesService.LikesNumbers(id);
}
