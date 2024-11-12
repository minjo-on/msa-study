package com.nninjoon.userservice.filter;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nninjoon.userservice.domain.UserEntity;
import com.nninjoon.userservice.jwt.TokenProvider;
import com.nninjoon.userservice.vo.RequestLogin;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final TokenProvider tokenProvider;

	public AuthenticationFilter(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
		setFilterProcessesUrl("/login");
	}

	@Override
	public Authentication attemptAuthentication(
		HttpServletRequest request,
		HttpServletResponse response
	) throws AuthenticationException {
		try{
			RequestLogin creds = new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);

			return getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(
					creds.email(),
					creds.password(), new ArrayList<>()
				)
			);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain chain,
		Authentication auth
	) {
		String userId = ((UserEntity) auth.getPrincipal()).getUsername();
		String accessToken = tokenProvider.generateToken(userId, Duration.ofHours(2));

		response.addHeader("Authorization", "Bearer " + accessToken);
	}
}
