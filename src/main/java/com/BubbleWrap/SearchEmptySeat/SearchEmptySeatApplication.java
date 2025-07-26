package com.BubbleWrap.SearchEmptySeat;

import com.BubbleWrap.SearchEmptySeat.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
@EnableScheduling
public class SearchEmptySeatApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchEmptySeatApplication.class, args);
	}

}
