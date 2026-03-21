package com.zybrof.url_shortener.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShorteningOutDto {
    private String originalUrl;
    private String shortenedUrl;
    private String code;
}
