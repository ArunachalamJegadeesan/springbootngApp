package com.springboot.sso.service;


import com.springboot.sso.service.gateway.resources.DefaultHandler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.server.*;
import java.security.Principal;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.security.Principal;

@SpringBootApplication
@EnableWebFluxSecurity
public class SSOServiceApplication {

	 public static void main(String[] args)
	{
		SpringApplication.run(SSOServiceApplication.class, args);
	}

	@Bean
	public SecurityWebFilterChain configure(ServerHttpSecurity http) throws Exception {
		return http.authorizeExchange()
				.anyExchange().authenticated()
				.and().oauth2Login()
				.and().build();
	}
}