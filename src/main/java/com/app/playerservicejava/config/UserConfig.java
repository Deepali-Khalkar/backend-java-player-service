package com.app.playerservicejava.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserConfig {

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        System.out.println("Creating in-memory user details service - start");
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        // Define an admin user
        manager.createUser(User.withUsername("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .build());

        // Define a normal user
        manager.createUser(User.withUsername("user")
                .password(passwordEncoder.encode("user123"))
                .roles("USER")
                .build());
        System.out.println("Creating in-memory user details service - end");
        return manager;
    }
}