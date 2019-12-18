package com.springboot.sso.service.gateway.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Controller
public class DefaultHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/app/***")
    public Mono<String> handlieIntialBurst(Principal principal, ServerResponse response)
    {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken)principal;
        logger.info("Prinicipal ->", token.getPrincipal().getAttributes());
        response.headers().add("New", "new");
        System.out.println("Attributes:"+token.getPrincipal().getAttributes());
        return Mono.just("Welcome page redirect shoud be done");
    }




}
