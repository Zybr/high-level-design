package com.zybrof.url_shortener.src.controller;

import com.zybrof.url_shortener.src.dto.ShorteningInpDto;
import com.zybrof.url_shortener.src.dto.ShorteningOutDto;
import com.zybrof.url_shortener.src.service.Shortener;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/shorten")
@RequiredArgsConstructor
public class ShortenerController {
    private final Shortener shortener;

    @PostMapping
    public ShorteningOutDto createShortening(
            @Valid @RequestBody ShorteningInpDto inpDto
    ) {
        return shortener.defineShortening(inpDto);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Void> redirect(
            @PathVariable String code
    ) {
        Optional<ShorteningOutDto> shortening = shortener.getShortening(code);

        if (shortening.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", shortening.get().getOriginalUrl());
            return ResponseEntity
                    .status(302)
                    .headers(headers)
                    .build();
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

}
