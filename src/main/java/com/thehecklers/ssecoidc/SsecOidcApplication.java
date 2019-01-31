package com.thehecklers.ssecoidc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class SsecOidcApplication {
    @Bean
    WebClient webClient(ClientRegistrationRepository regRepo, OAuth2AuthorizedClientRepository clientRepo) {
        ServletOAuth2AuthorizedClientExchangeFilterFunction oauthEFF =
                new ServletOAuth2AuthorizedClientExchangeFilterFunction(regRepo, clientRepo);

        oauthEFF.setDefaultOAuth2AuthorizedClient(true);

        return WebClient.builder().apply(oauthEFF.oauth2Configuration())
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(SsecOidcApplication.class, args);
    }

}

@RestController
class LoginController {
    private final WebClient webClient;

    LoginController(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping
    String hello() {
        return "Hola mundo!";
    }

    @GetMapping("/something")
    String getSomething() {
        return webClient.get()
                .uri("http://localhost:8081/something")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @GetMapping("/claims")
    String getClaims() {
        return webClient.get()
                .uri("http://localhost:8081/claims")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @GetMapping("/email")
    String getSubscriberEmail() {
        return webClient.get()
                .uri("http://localhost:8081/email")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

}