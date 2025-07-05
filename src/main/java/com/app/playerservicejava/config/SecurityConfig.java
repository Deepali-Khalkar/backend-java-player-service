package com.app.playerservicejava.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // Enables method-level security
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("securityFilterChain - start");
        http
        .csrf(csrf -> csrf.disable()) // Explicitly disable CSRF
        .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/v1/players").hasRole("USER") // Use requestMatchers instead of antMatchers
                .anyRequest().authenticated()
        )
                .headers(headers -> headers.frameOptions().disable()) // Needed for H2 console to render iframe

                .httpBasic(Customizer.withDefaults()); // Use Basic authentication for simplicity
        System.out.println("securityFilterChain - end");
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        System.out.println("inside PasswordEncoder");
        return new BCryptPasswordEncoder();
    }

    /*

    @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtFilter) throws Exception {
    http.csrf().disable()
        .authorizeRequests()
        .antMatchers("/v1/players").hasRole("USER") // Role-based access
        .anyRequest().authenticated()
        .and()
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter

    return http.build();
}

     */
}