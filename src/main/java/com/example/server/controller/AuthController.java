package com.example.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.dto.SignatureRequest;
import com.example.server.dto.response.AuthResponse;
import com.example.server.dto.response.NonceResponse;
import com.example.server.entity.User;
import com.example.server.jwt.JwtUtil;
import com.example.server.repository.UserRepository;
import com.example.server.service.AuthService;
import com.example.server.service.NonceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "로그인 관련 API")
public class AuthController {
	@Autowired
	private AuthService authService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private NonceService nonceService;

	@Operation(summary = "nonce 발급 API")
	@GetMapping("/nonce")
	public ResponseEntity<NonceResponse> getNonce(@RequestParam String walletAddress) {
		if (walletAddress == null || walletAddress.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}

		String nonce = nonceService.generateNonce(walletAddress);
		return ResponseEntity.ok().body(new NonceResponse(nonce));

	}

	@Operation(summary = "서명검증 및 회원가입/로그인 API")
	@PostMapping("verify")
	public ResponseEntity<AuthResponse> verify(@RequestBody SignatureRequest signatureRequest) {

		//서명검증
		boolean isValid = authService.verifySignature(signatureRequest.getWalletAddress(),
			signatureRequest.getNonce(),
			signatureRequest.getSignature());
		if (!isValid) {
			return ResponseEntity.status(401).build();
		}

		//사용자 확인
		User user = userRepository.findByWalletAddress(signatureRequest.getWalletAddress())
			.orElseGet(() -> { //DB에 없으면 자동 회원가입
				User newUser = new User(signatureRequest.getWalletAddress());
				return userRepository.save(newUser);
			});

		//JWT 토큰 발급
		String accessToken = jwtUtil.generateAccessToken(user.getId(), signatureRequest.getWalletAddress());
		String refreshToken = jwtUtil.generateRefreshToken(user.getId());

		return ResponseEntity.ok().body(new AuthResponse(accessToken, refreshToken));

	}

}