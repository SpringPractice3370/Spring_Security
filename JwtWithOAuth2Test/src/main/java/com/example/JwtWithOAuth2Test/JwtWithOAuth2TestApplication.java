package com.example.JwtWithOAuth2Test;

import com.example.JwtWithOAuth2Test.config.properties.AppProperties;
import com.example.JwtWithOAuth2Test.config.properties.CorsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
		AppProperties.class,
		CorsProperties.class
})
public class JwtWithOAuth2TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtWithOAuth2TestApplication.class, args);
	}

}
