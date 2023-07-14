package com.sparta.vlog.service;

import com.sparta.vlog.dto.SignupRequestDto;
import com.sparta.vlog.entity.User;
import com.sparta.vlog.entity.UserRoleEnum;
import com.sparta.vlog.exception.NotFoundException;
import com.sparta.vlog.jwt.JwtUtil;
import com.sparta.vlog.repository.UserRepository;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final MessageSource messageSource;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, MessageSource messageSource) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.messageSource = messageSource;
    }

    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new NotFoundException(messageSource.getMessage(
                    "not.found.User",
                    null,
                    "중복된 사용자가 존재합니다.",
                    Locale.getDefault()
            ));
        }

        // email 중복확인
        String email = requestDto.getEmail();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new NotFoundException(messageSource.getMessage(
                    "not.found.Email",
                    null,
                    "중복된 Email 입니다.",
                    Locale.getDefault()
            ));
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new NotFoundException(messageSource.getMessage(
                        "not.found.AdminPassword",
                        null,
                        "관리자 암호가 틀려 등록이 불가능합니다.",
                        Locale.getDefault()
                ));
            }
            role = UserRoleEnum.ADMIN;
        }

        // 사용자 등록
        User user = new User(username, password, email, role);
        userRepository.save(user);
    }
}