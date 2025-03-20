package com.mycompany.sginegocio.utilities;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/SGI/api/users/register"))
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/SGI/api/users/register").permitAll()
                .anyRequest().authenticated())
            .httpBasic(withDefaults());
        return http.build();
    }
}