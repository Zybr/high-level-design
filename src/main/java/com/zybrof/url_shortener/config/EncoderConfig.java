package com.zybrof.url_shortener.config;

import com.zybrof.url_shortener.service.Encoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncoderConfig {
    @Bean
    public Encoder encoder62() {
        return new Encoder("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }
}
