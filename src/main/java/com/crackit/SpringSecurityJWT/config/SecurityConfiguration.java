package com.crackit.SpringSecurityJWT.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;



    @Configuration
    @EnableWebSecurity
    @RequiredArgsConstructor
    @EnableMethodSecurity
    public class SecurityConfiguration {
        private final AuthenticationProvider authenticationProvider;
        private final JwtAuthFilter jwtAuthFilter;
        private  final AccessDeniedHandler accessDeniedHandler;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            return http
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(req ->req
                            .requestMatchers("/crackit/v1/auth/**").permitAll() // Public access to authentication endpoints
                            .requestMatchers("/crackit/v1/management/**").hasAnyRole("ADMIN", "MEMBER") // Role-based access
                            .anyRequest().authenticated() // All other requests need to be authenticated
                    )
                    .cors(cors -> cors
                            .configurationSource(request -> {
                                CorsConfiguration configuration = new CorsConfiguration();
                                configuration.setAllowedOrigins(List.of("http://localhost:3008")); // Allow this specific origin
                                configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Allow necessary methods
                                configuration.setAllowedHeaders(List.of("*")); // Allow all headers
                                return configuration;
                            })
                    )
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless sessions
                    .authenticationProvider(authenticationProvider) // Custom authentication provider
                    .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // JWT authentication filter
                    .build();
        }
    }


