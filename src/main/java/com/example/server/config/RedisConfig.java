package com.example.server.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisConfig {
	@Value("${spring.data.redis.host}")
	String host;
	@Value("${spring.data.redis.port}")
	int port;
	@Value("${spring.data.redis.password}")
	String password;

	//0번 DB (Nonce 저장)
	@Bean(name = "nonceRedisConnectionFactory")
	public RedisConnectionFactory nonceRedisConnectionFactory() {
		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
		config.setPassword(password);
		config.setDatabase(0);
		return new LettuceConnectionFactory(config);
	}

	//1번 DB (Refresh Token 저장)
	@Bean(name = "refreshTokenRedisConnectionFactory")
	public RedisConnectionFactory refreshTokenRedisConnectionFactory() {
		RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
		config.setPassword(password);
		config.setDatabase(1);
		return new LettuceConnectionFactory(config);
	}

	//Nonce 저장용 RedisTemplate
	@Bean(name = "nonceRedisTemplate")
	public StringRedisTemplate nonceRedisTemplate(
		@Qualifier("nonceRedisConnectionFactory") RedisConnectionFactory connectionFactory) {
		return new StringRedisTemplate(connectionFactory);

	}

	//Refresh Token 저장용 RedisTemplate
	@Bean(name = "refreshTokenRedisTemplate")
	public StringRedisTemplate refreshTokenRedisTemplate(
		@Qualifier("refreshTokenRedisConnectionFactory") RedisConnectionFactory connectionFactory) {
		return new StringRedisTemplate(connectionFactory);
	}
}
