package com.example.server.jwt;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component

public class JwtUtil {
	private final Key key;

	public JwtUtil(@Value("${jwt.secret}") String secretKey) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	//AccessToken
	public String generateAccessToken(Long userId, String walletAddress) {
		return Jwts.builder()
			.setSubject(walletAddress)
			.claim("user_id", userId)
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1시간 유효
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	//RefreshToken
	public String generateRefreshToken(Long userId) {
		return Jwts.builder()
			.setSubject(userId.toString()) // refreshToken은 보통 userId만 사용
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 14)) // 14일 유효
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	public Claims extractClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}

	public Long extractUserId(String token) {
		Claims claims = extractClaims(token);
		return claims.get("user_id", Long.class);
	}

	public boolean isTokenExpired(String token) {
		return extractClaims(token).getExpiration().before(new Date());
	}
}



