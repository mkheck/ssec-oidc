package com.thehecklers.ssecoidc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SsecOidcApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsecOidcApplication.class, args);
	}

}

/*@EnableWebSecurity
class WebConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/").permitAll()
				.anyRequest().authenticated();
//				.and().oauth2Client()
//				.and().oauth2Login();
	}
}*/

@RestController
class SimpleController {
	@GetMapping
	String hello() {
		return "Hello!";
	}
}