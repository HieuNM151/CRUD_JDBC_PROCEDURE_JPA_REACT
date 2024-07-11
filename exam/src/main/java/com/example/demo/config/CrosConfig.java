package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrosConfig {
    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000") // Chỉ cho phép nguồn này
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Chỉ cho phép các phương thức này
                        .allowedHeaders("*") // Cho phép tất cả các header
                        .allowCredentials(true) // Cho phép gửi thông tin xác thực (cookies)
                        .maxAge(3600); // Thời gian tối đa (giây) mà trình duyệt có thể cache kết quả của preflight request
            }
        };
    }
}
