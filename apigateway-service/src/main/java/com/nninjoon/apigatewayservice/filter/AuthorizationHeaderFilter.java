package com.nninjoon.apigatewayservice.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

	public AuthorizationHeaderFilter() {
		super(Config.class); // Config 클래스 설정
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			String authorizationHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

			if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
				return onError(exchange, "Missing or invalid Authorization header", HttpStatus.UNAUTHORIZED);
			}

			return chain.filter(exchange);
		};
	}
	// 필터 오류 응답 처리 메서드
	private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
		exchange.getResponse().setStatusCode(httpStatus);

		log.error(err);
		return exchange.getResponse().setComplete();
	}

	// Config 클래스 (필요 시 추가 설정 값)
	public static class Config {
		// 설정 값이 필요 없다면 빈 클래스로 유지
	}
}
