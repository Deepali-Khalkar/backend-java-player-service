package com.app.playerservicejava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.app.playerservicejava.repository")

public class PlayerServiceJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlayerServiceJavaApplication.class, args);
    }

}
