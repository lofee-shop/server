package com.example.server.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.dto.request.LogoutRequest;
import com.example.server.dto.request.SignatureRequest;
import com.example.server.dto.response.AuthResponse;
import com.example.server.dto.response.NonceResponse;
import com.example.server.exception.CustomException;
import com.example.server.exception.ResponseCode;
import com.example.server.jwt.JwtUtil;
import com.example.server.service.AuthService;
import com.example.server.service.NonceService;
import com.example.server.service.RefreshtokenService;
import com.example.server.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "로그인 관련 API")
public class AuthController {

	private final AuthService authService;
	private final JwtUtil jwtUtil;
	private final NonceService nonceService;
	private final RefreshtokenService refreshtokenService;
	private final UserService userService;

	@Operation(summary = "nonce 발급 API", responses = {
		@ApiResponse(responseCode = "200", description = "nonce 발급 성공"),
		@ApiResponse(responseCode = "401", description = "지갑 주소가 유효하지 않음")
	})
	@PostMapping("/nonce")
	public ResponseEntity<NonceResponse> getNonce(@RequestBody String walletAddress) {
		if (walletAddress == null || walletAddress.isEmpty()) {
			throw new CustomException(ResponseCode.WALLET_NOT_FOUND);
		}

		String nonce = nonceService.generateNonce(walletAddress);
		return ResponseEntity.ok().body(new NonceResponse(nonce));

	}

	@Operation(summary = "서명검증 및 회원가입/로그인 API", responses = {
		@ApiResponse(responseCode = "200", headers = {
			@Header(name = HttpHeaders.SET_COOKIE, description = "Refresh Token"),
			@Header(name = HttpHeaders.AUTHORIZATION, description = "Access Token")
		}),
		@ApiResponse(responseCode = "404", description = "사용자가 유효하지 않음")
	})

	@PostMapping("/verify")
	public ResponseEntity<Boolean> verify(@RequestBody SignatureRequest signatureRequest) {
		AuthResponse authResponse = authService.verifySignature(signatureRequest.message(),
			signatureRequest.signature());

		if (authResponse.accessToken() == null || authResponse.refreshToken() == null)
			throw new CustomException(ResponseCode.USER_NOT_FOUND);

		ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", authResponse.refreshToken())
			.httpOnly(true)
			.secure(true) //로컬 개발시 false
			.path("/")
			.maxAge(14 * 24 * 60 * 60) //14일 유지
			.build();

		return ResponseEntity.ok()
			.header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
			.header(HttpHeaders.AUTHORIZATION, "Bearer " + authResponse.accessToken())
			.body(true);
	}

	@Operation(summary = "로그아웃 API", responses = {
		@ApiResponse(responseCode = "200", description = "로그아웃 성공"),
		@ApiResponse(responseCode = "404", description = "사용자가 유효하지 않음")
	})
	@PostMapping("/logout")
	public void logout(@RequestBody LogoutRequest request) {
		Long userid = userService.findUserIdbyWalletAddress(request.walletAddress());
		refreshtokenService.deleteRefreshToken(userid);
	}

	@Operation(summary = "accessToken 만료 시 refreshToken으로 재발급", description = "swagger 상에선 실제로 쿠키 넣어줘야 테스트 가능", responses = {
		@ApiResponse(responseCode = "200", description = "토큰 재발행 성공"),
		@ApiResponse(responseCode = "404", description = "사용자가 유효하지 않음")
	})
	@PostMapping("/refresh")
	public ResponseEntity<AuthResponse> refreshAccessToken(
		@CookieValue(value = "refreshToken", required = false) String refreshToken) {
		if (refreshToken == null || refreshToken.isEmpty()) {
			throw new CustomException(ResponseCode.USER_NOT_FOUND);
		}

		//토큰에서 userid 검출
		Long userId = jwtUtil.extractUserIdFromRefreshToken(refreshToken);

		//Redis에서 Refresh Token 확인
		String storedRefreshToken = refreshtokenService.getRefreshToken(userId);
		if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)) {
			throw new CustomException(ResponseCode.USER_NOT_FOUND);
		}

		//새로운 Access Token 발급
		String wallet_address = userService.findWalletAddressByUserId(userId);
		String newAccessToken = jwtUtil.generateAccessToken(userId, wallet_address);
		return ResponseEntity.ok().body(new AuthResponse(newAccessToken, refreshToken));
	}
}