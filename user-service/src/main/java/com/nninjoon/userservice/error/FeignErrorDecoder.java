package com.nninjoon.userservice.error;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FeignErrorDecoder implements ErrorDecoder {
	private final Environment env;

	@Override
	public Exception decode(String s, Response response) {
		switch (response.status()) {
			case 400:
				break;
			case 404:
				if (s.contains("getOrders")) {
					return new ResponseStatusException(HttpStatus.valueOf(response.status()),
						env.getProperty("order_service.exception.orders_is_empty"));
				}
				break;
			default:
				return new Exception(response.reason());
		}

		return null;
	}
}
