package com.springboot.sso.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@SpringBootApplication
@Controller
public class SSOServiceApplication {


	static final String FORWARDED_URL = "X-CF-Forwarded-Url";

	static final String PROXY_METADATA = "X-CF-Proxy-Metadata";

	static final String PROXY_SIGNATURE = "X-CF-Proxy-Signature";

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private RestTemplate restTemplate;



	@RequestMapping(value="/")
	public ResponseEntity <?> service (RequestEntity<?> request, Principal principal) throws IOException, URISyntaxException
	{
		OAuth2AuthenticationToken token = (OAuth2AuthenticationToken)principal;
		logger.info("Prinicipal ->", token.getPrincipal().getAttributes());
		logger.info("incoming  Request:  {}", formatRequest(request.getMethod(), request.getUrl(), request.getHeaders()));
		RequestEntity<?>  outrequest = getForwardRequest(request);
		logger.info("Outgoing Request:  {}", formatRequest(outrequest.getMethod(), outrequest.getUrl(), outrequest.getHeaders()));
        return  restTemplate.exchange( outrequest, byte[].class);
	}

	private static String formatRequest(HttpMethod method, URI uri, HttpHeaders headers) {
		return String.format("%s %s, %s", method, uri, headers);
	}

	private RequestEntity<?> getForwardRequest(RequestEntity <?> incoming)
	{
	 HttpHeaders headers = new HttpHeaders();
	 headers.putAll(incoming.getHeaders());
	 URI uri = headers.remove(FORWARDED_URL).stream().findFirst().map(URI::create).orElseThrow(() ->
		new IllegalStateException("No forward url present"+FORWARDED_URL));

     return new RequestEntity<> (incoming.getBody(), headers, incoming.getMethod(),uri );
	}


	@Bean
	 RestTemplate restTemplate(){
		return new RestTemplate();
	}

	 public static void main(String[] args)
	{
		SpringApplication.run(SSOServiceApplication.class, args);
	}




}