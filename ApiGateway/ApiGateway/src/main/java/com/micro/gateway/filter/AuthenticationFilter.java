package com.micro.gateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import com.google.common.net.HttpHeaders;
import com.micro.gateway.services.JwtService;
import com.micro.gateway.vaidator.RouteValidator;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

	@Autowired
	private RouteValidator validator;

	@Autowired
	private JwtService jwtService;

	// constructor
	public AuthenticationFilter() {
		super(Config.class);
	}

	// this is config class
	public static class Config {

	}

	@Override
	public GatewayFilter apply(Config config) {

		return ((exchange, chain) -> {

			if (validator.isSecured.test(exchange.getRequest())) {

				// checking header contains token or not
				if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
					throw new RuntimeException("Header is missing in request");
				}

				String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

				if (authHeader != null && authHeader.startsWith("Bearer ")) {
					authHeader = authHeader.substring(7);
				}

//				try {
//					template.getForObject("http://AuthService/validate?token=" + authHeader, String.class);
//				} catch(Exception e) {
//					System.out.println("invalid access");
//					throw new RuntimeException("access failed");
//				}

				try {
//                  //REST call to AUTH service
//                  template.getForObject("http://IDENTITY-SERVICE//validate?token" + authHeader, String.class);
					jwtService.validateToken(authHeader);

				} catch (Exception e) {
					System.out.println("invalid access...!");
					throw new RuntimeException("un authorized access to application");
				}
			}

			return chain.filter(exchange);
		});
	}

}
