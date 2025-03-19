package com.example.server.service;

import java.util.Optional;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.server.dto.response.AuthResponse;
import com.example.server.entity.User;
import com.example.server.entity.enums.Role;
import com.example.server.exception.CustomException;
import com.example.server.exception.ResponseCode;
import com.example.server.jwt.JwtUtil;
import com.example.server.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

	private final NonceService nonceService;
	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;
	private final RefreshtokenService refreshtokenService;

	@Transactional
	public AuthResponse verifySignature(String message, String signature) {

		String walletAddress = extractWalletAddress(message)
			.orElseThrow(() -> new CustomException(ResponseCode.WALLET_NOT_FOUND));

		String nonce = extractNonce(message)
			.orElseThrow(() -> new CustomException(ResponseCode.NONCE_ERR));

		//Redis에 저장된 nonce 가져오기
		String storedNonce = nonceService.getNonce(walletAddress);
		System.out.println(storedNonce);
		//Redis에 저장된 nonce와 다르면 인증 x
		if (storedNonce == null || !storedNonce.equals(nonce)) {
			return new AuthResponse(null, null);
		}

		//서명 검증 및 지갑 주소 복원
		String recoveredAddress = EthereumSignatureService.recoverAddressFromSignature(message, signature);
		System.out.println(recoveredAddress);

		//복원된 주소와 입력된 주소 비교
		if (!walletAddress.equalsIgnoreCase(recoveredAddress)) {
			return new AuthResponse(null, null);
		}

		//nonce 삭제 (일회성 사용)
		nonceService.deleteNonce(walletAddress);

		//사용자 확인
		User user = userRepository.findByWalletAddress(walletAddress)
			.orElseGet(() -> { //DB에 없으면 자동 회원가입
				User newUser = new User(walletAddress, Role.BUYER, true);
				newUser.setNickname("buyer" + new Random().nextInt(900000) + 100000);
				return userRepository.save(newUser);
			});

		//JWT 토큰 발급
		String accessToken = jwtUtil.generateAccessToken(user.getId(), walletAddress);
		String refreshToken = jwtUtil.generateRefreshToken(user.getId());

		//refreshToken 저장
		refreshtokenService.saveRefreshToken(user.getId(), refreshToken);
		return new AuthResponse(accessToken, refreshToken);
	}

	public static Optional<String> extractWalletAddress(String message) {
		Pattern pattern = Pattern.compile("(?i)\\n(0x[a-fA-F0-9]{40})\\n");
		Matcher matcher = pattern.matcher(message);
		return matcher.find() ? Optional.of(matcher.group(1)) : Optional.empty();
	}

	public static Optional<String> extractNonce(String message) {
		Pattern pattern = Pattern.compile("Nonce:\\s(\\S+)");
		Matcher matcher = pattern.matcher(message);
		return matcher.find() ? Optional.of(matcher.group(1)) : Optional.empty();
	}
}
