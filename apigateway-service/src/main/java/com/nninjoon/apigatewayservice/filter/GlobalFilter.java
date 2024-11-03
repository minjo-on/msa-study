package com.nninjoon.apigatewayservice.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalFilter extends AbstractGatewayFilterFactory<GlobalFilter.Config> {

	public GlobalFilter() {
		super(Config.class);
	}

	@Override
	public GatewayFilter apply(Config config) {
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			ServerHttpResponse response = exchange.getResponse();

			log.info("Global Filter baseMessage:  {}", config.getBaseMessage());
			if (config.isPreLogger()){
				log.info("Global Filter Start: request id -> {}", request.getId());
			}
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				if (config.isPostLogger()){
					log.info("Global Filter End: response code -> {}", response.getStatusCode());
				}
			}));
		};
	}

	@Data
	public static class Config {
		// 필요한 설정 필드를 여기에 추가할 수 있습니다.
		private String baseMessage;
		private boolean preLogger;
		private boolean postLogger;
	}
}
