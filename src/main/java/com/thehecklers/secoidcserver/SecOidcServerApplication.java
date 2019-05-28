package com.thehecklers.secoidcserver;

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
public class SecOidcServerApplication {
	@Bean
	WebClient client(ClientRegistrationRepository regRepo, OAuth2AuthorizedClientRepository cliRepo) {
		ServletOAuth2AuthorizedClientExchangeFilterFunction function =
				new ServletOAuth2AuthorizedClientExchangeFilterFunction(regRepo, cliRepo);

		function.setDefaultOAuth2AuthorizedClient(true);

		return WebClient.builder()
				.baseUrl("http://localhost:8081/resources")
				.apply(function.oauth2Configuration())
				.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(SecOidcServerApplication.class, args);
	}

}

@RestController
class OidcController {

	private final  WebClient client;

	OidcController(WebClient client) {
		this.client = client;
	}

	@GetMapping("/")
	String hello() {
		return "Hello jPrime!!!!";
	}

	@GetMapping("/something")
	String getSomething() {
		return client.get()
				.uri("/something")
				.retrieve()
				.bodyToMono(String.class)
				.block();
	}

	@GetMapping("/claims")
	String getClaims() {
		return client.get()
				.uri("/claims")
				.retrieve()
				.bodyToMono(String.class)
				.block();
	}

	@GetMapping("/email")
	String getEmail() {
		return client.get()
				.uri("/email")
				.retrieve()
				.bodyToMono(String.class)
				.block();
	}
}
