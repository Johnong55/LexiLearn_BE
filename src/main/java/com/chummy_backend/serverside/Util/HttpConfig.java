// Remove AIConfiguration.java completely since it's redundant
// OR keep it but remove the ObjectMapper bean

// HttpConfig.java - Updated version
package com.chummy_backend.serverside.Util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.time.Duration;

@Configuration
public class HttpConfig {
    
    /**
     * Tạo RestTemplate Bean với cấu hình timeout
     */
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
            .setConnectTimeout(Duration.ofSeconds(30))
            .setReadTimeout(Duration.ofSeconds(30))
            .build();
    }
}
