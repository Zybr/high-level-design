package com.zybrof.url_shortener.service;

import com.zybrof.url_shortener.config.ShorteningProperties;
import com.zybrof.url_shortener.dto.ShorteningInpDto;
import com.zybrof.url_shortener.dto.ShorteningOutDto;
import com.zybrof.url_shortener.entity.Shortening;
import com.zybrof.url_shortener.repository.ShorteningRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class Shortener {
    private final ShorteningRepository repository;
    private final Encoder encoder;
    private final ShorteningProperties properties;

    public Optional<ShorteningOutDto> getShortening(
            String code
    ) {
        return repository
                .findByCode(code)
                .flatMap(entity -> Optional.of(mapToOutDto(entity)));
    }

    public ShorteningOutDto defineShortening(
            ShorteningInpDto inpDto
    ) {
        return mapToOutDto(
                getOrCreateEntity(
                        inpDto.getUrl()
                )
        );
    }

    private Shortening getOrCreateEntity(String url) {
        return this.repository
                .findByUrl(url)
                .orElseGet(() -> createEntity(url));
    }

    private Shortening createEntity(String url) {
        Shortening shortening = this.repository
                .save(new Shortening(url));

        shortening.setCode(
                encoder.encodeInt(
                        shortening.getId()
                )
        );

        return repository.save(shortening);
    }

    private ShorteningOutDto mapToOutDto(Shortening entity) {
        return new ShorteningOutDto(
                entity.getUrl(),
                this.getFullUrl(
                        entity.getCode()
                ),
                entity.getCode()
        );
    }

    private String getFullUrl(String code) {
        return properties.getBaseUrl() + '/' + code;
    }
}
