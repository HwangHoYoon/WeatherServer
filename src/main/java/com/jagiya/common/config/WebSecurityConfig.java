package com.jagiya.common.config;

import com.jagiya.common.filter.JwtAuthFilter;
import com.jagiya.common.service.JwtAuthenticationEntryPoint;
import com.jagiya.common.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer ignoringCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/h2-console/**", "/swagger-ui/**", "/swagger-client/**", "/api-docs/**", "/css/**", "/js/**", "/json/**", "/image/**",
        "/v3/api-docs/**",
           "/swagger-ui.html",
                "/user/login"


        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.cors();
        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //http.authorizeRequests().requestMatchers("/user/login").permitAll()

        http.authorizeRequests().requestMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and().addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint())

        ;

        return http.build();

    }
}
