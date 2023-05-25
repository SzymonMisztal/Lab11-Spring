package com.example.demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfiguration(@Lazy UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable().authorizeHttpRequests()
                .requestMatchers("/admin/**")
                .hasAuthority("ADMIN")
                .requestMatchers("/user/**")
                .hasAuthority("USER")
                .requestMatchers("/login*")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                        .successForwardUrl("/index"))
                .userDetailsService(userDetailsService);

        return http.build();
    }

}
