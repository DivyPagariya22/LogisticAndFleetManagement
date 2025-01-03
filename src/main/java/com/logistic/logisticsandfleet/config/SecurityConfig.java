package com.logistic.logisticsandfleet.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.logistic.logisticsandfleet.interceptor.JWTInterceptor;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {

    @Autowired
    private JWTInterceptor jwtInterceptor;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll() // Allows access to all endpoints
                                                                                   // without authentication
        ).csrf(csrf -> csrf.disable());
        return http.build();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor).addPathPatterns("/**").excludePathPatterns("/auth/login",
                "/auth/signup"); // Apply interceptor to all paths
    }

    // Configure global CORS settings
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @SuppressWarnings("null")
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("http://localhost:3000") // React app URL
                        .allowedMethods("GET", "POST", "PUT", "DELETE").allowedHeaders("*").allowCredentials(true);
            }
        };
    }
}
