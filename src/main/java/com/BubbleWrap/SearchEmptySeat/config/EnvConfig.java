package com.BubbleWrap.SearchEmptySeat.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvConfig {

    @PostConstruct
    public void loadEnv() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

        System.setProperty("MAIL_USERNAME", dotenv.get("MAIL_USERNAME"));
        System.setProperty("MAIL_PASSWORD", dotenv.get("MAIL_PASSWORD"));
    }

    @PostConstruct
    public void checkEnvVariables() {
        System.out.println("MAIL_USERNAME: " + System.getProperty("MAIL_USERNAME"));
        System.out.println("MAIL_PASSWORD: " + System.getProperty("MAIL_PASSWORD"));
    }

}
