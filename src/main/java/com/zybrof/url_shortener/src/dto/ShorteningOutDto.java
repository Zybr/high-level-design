package com.zybrof.url_shortener.src.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShorteningOutDto {
    private String originalUrl;
    private String shortenedUrl;
    private String code;
}
