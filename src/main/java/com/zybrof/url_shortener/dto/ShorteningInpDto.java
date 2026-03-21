package com.zybrof.url_shortener.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShorteningInpDto {
    @NotBlank(message = "URL cannot be blank")
    @URL(message = "Invalid URL format")
    private String url;
}
