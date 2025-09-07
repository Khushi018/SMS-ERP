package com.aristiec.schoolmanagementsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        // Allow your frontend and Swagger UI origins
        corsConfig.setAllowedOrigins(List.of( 
                "http://localhost:5173",
                "https://sms02-461210.el.r.appspot.com", 
                "https://6e1a53647dbe.ngrok-free.app",
                "http://localhost:9000",
                "https://sms-black-three.vercel.app"
                ));


        // Allow required HTTP methods
        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        corsConfig.setAllowedHeaders(List.of(
                "Authorization",
                "Content-Type",
                "X-Requested-With"
        ));

        corsConfig.setAllowCredentials(true); // Required if using cookies or authentication headers
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return new CorsFilter(source);
    }
}
