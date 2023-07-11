package com.sparta.vlog.service;

import com.sparta.vlog.repository.LikesRepository;
import com.sparta.vlog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikesService {
    private final PostRepository postRepository;
    private final LikesRepository likesRepository;


}
