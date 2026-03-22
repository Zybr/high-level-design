package com.zybrof.url_shortener.src.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "shortening")
@PropertySource("classpath:shortening.properties")
@Component
@Getter
@Setter
public class ShorteningProperties {
    private String baseUrl;
}
