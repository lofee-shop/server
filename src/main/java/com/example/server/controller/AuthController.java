package com.example.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.dto.request.SignatureRequest;
import com.example.server.dto.response.AuthResponse;
import com.example.server.dto.response.NonceResponse;
import com.example.server.jwt.JwtUtil;
import com.example.server.repository.UserRepository;
import com.example.server.service.AuthService;
import com.example.server.service.NonceService;
import com.example.server.service.RefreshtokenService;

import io.jsonwebtoken.Claims;
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
	@Autowired
	private RefreshtokenService refreshtokenService;

	@Operation(summary = "nonce 발급 API", description = "지갑 주소 입력")
	@PostMapping("/nonce")
	public ResponseEntity<NonceResponse> getNonce(@RequestBody String walletAddress) {
		if (walletAddress == null || walletAddress.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}

		String nonce = nonceService.generateNonce(walletAddress);
		return ResponseEntity.ok().body(new NonceResponse(nonce));

	}

	@SuppressWarnings("checkstyle:WhitespaceAfter")
	@Operation(summary = "서명검증 및 회원가입/로그인 API")
	@PostMapping("verify")
	public AuthResponse verify(@RequestBody SignatureRequest signatureRequest) {
		return authService.verifySignature(signatureRequest.getMessage(), signatureRequest.getSignature());
	}

	@Operation(summary = "accessToken 만료 시 refreshToken으로 재발급")
	@PostMapping("/refresh")
	public ResponseEntity<AuthResponse> refreshAccessToken(@RequestBody String refreshToken) {
		try {
			//토큰에서 userid 검출
			Claims claims = jwtUtil.extractClaims(refreshToken);
			Long userId = Long.valueOf(claims.getSubject());

			//Redis에서 Refresh Token 확인
			String storedRefreshToken = refreshtokenService.getRefreshToken(userId);
			if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)) {
				return ResponseEntity.status(401).build();
			}

			// ✅ 새로운 Access Token 발급
			String newAccessToken = jwtUtil.generateAccessToken(userId, "wallet_address");
			return ResponseEntity.ok().body(new AuthResponse(newAccessToken, refreshToken));

		} catch (Exception e) {
			return ResponseEntity.status(401).build();
		}
	}
}