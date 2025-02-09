package com.BubbleWrap.SearchEmptySeat;

import com.BubbleWrap.SearchEmptySeat.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class SearchEmptySeatApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchEmptySeatApplication.class, args);
	}

}
