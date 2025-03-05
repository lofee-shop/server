package com.example.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.dto.request.SignatureRequest;
import com.example.server.dto.response.AuthResponse;
import com.example.server.dto.response.NonceResponse;
import com.example.server.jwt.JwtUtil;
import com.example.server.service.AuthService;
import com.example.server.service.NonceService;
import com.example.server.service.RefreshtokenService;
import com.example.server.service.UserService;

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
	private JwtUtil jwtUtil;
	@Autowired
	private NonceService nonceService;
	@Autowired
	private RefreshtokenService refreshtokenService;
	@Autowired
	private UserService userService;

	@Operation(summary = "nonce 발급 API", description = "지갑 주소 입력")
	@PostMapping("/nonce")
	public ResponseEntity<NonceResponse> getNonce(@RequestBody String walletAddress) {
		if (walletAddress == null || walletAddress.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}

		String nonce = nonceService.generateNonce(walletAddress);
		return ResponseEntity.ok().body(new NonceResponse(nonce));

	}

	@Operation(summary = "서명검증 및 회원가입/로그인 API")
	@PostMapping("verify")
	public ResponseEntity<AuthResponse> verify(@RequestBody SignatureRequest signatureRequest) {
		AuthResponse authResponse = authService.verifySignature(signatureRequest.getMessage(),
			signatureRequest.getSignature());

		ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", authResponse.getRefreshToken())
			.httpOnly(true)
			.secure(true) //로컬 개발시 false
			.path("/")
			.maxAge(14 * 24 * 60 * 60) //14일 유지
			.build();

		return ResponseEntity.ok()
			.header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
			.body(authResponse);

	}

	@Operation(summary = "로그아웃 API")
	@PostMapping("logout")
	public void logout(@RequestBody String walletAddress) {
		Long userid = userService.findUserIdbyWalletAddress(walletAddress);
		refreshtokenService.deleteRefreshToken(userid);
	}

	@Operation(summary = "accessToken 만료 시 refreshToken으로 재발급", description = "swagger 상에선 실제로 쿠키 넣어줘야 테스트 가능")
	@PostMapping("/refresh")
	public ResponseEntity<AuthResponse> refreshAccessToken(
		@CookieValue(value = "refreshToken", required = false) String refreshToken) {
		if (refreshToken == null || refreshToken.isEmpty()) {
			return ResponseEntity.status(401).build();
		}

		try {

			//토큰에서 userid 검출
			Claims claims = jwtUtil.extractClaims(refreshToken);
			Long userId = Long.valueOf(claims.getSubject());

			//Redis에서 Refresh Token 확인
			String storedRefreshToken = refreshtokenService.getRefreshToken(userId);
			if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)) {
				return ResponseEntity.status(401).build();
			}

			//새로운 Access Token 발급
			String wallet_address = userService.findWalletAddressByUserId(userId);
			String newAccessToken = jwtUtil.generateAccessToken(userId, wallet_address);
			return ResponseEntity.ok().body(new AuthResponse(newAccessToken, refreshToken));

		} catch (Exception e) {
			return ResponseEntity.status(401).build();
		}
	}
}