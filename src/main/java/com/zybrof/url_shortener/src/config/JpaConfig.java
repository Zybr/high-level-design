package com.zybrof.url_shortener.src.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.zybrof.url_shortener.repository")
@EntityScan(basePackages = "com.zybrof.url_shortener.entity")
public class JpaConfig {
}
