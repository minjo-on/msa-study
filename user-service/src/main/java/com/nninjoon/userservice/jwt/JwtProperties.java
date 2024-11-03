package com.nninjoon.userservice.jwt;

import javax.crypto.SecretKey;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component
public class JwtProperties {
	private String issuer;
	private SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
}

