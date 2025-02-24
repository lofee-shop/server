package com.example.server.controller;


import com.example.server.repository.UserRepository;
import com.example.server.service.NonceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "로그인(서명검증/JWT 토큰 발급) API")
public class AuthController {
    @Autowired
    private NonceService nonceService;

    @Autowired
    private UserRepository userRepository;
}
