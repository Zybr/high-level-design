package com.zybrof.url_shortener.service;

import com.zybrof.url_shortener.src.config.ShorteningProperties;
import com.zybrof.url_shortener.src.dto.ShorteningInpDto;
import com.zybrof.url_shortener.src.dto.ShorteningOutDto;
import com.zybrof.url_shortener.src.entity.Shortening;
import com.zybrof.url_shortener.src.repository.ShorteningRepository;
import com.zybrof.url_shortener.src.service.Encoder;
import com.zybrof.url_shortener.src.service.Shortener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShortenerTest {

    @Mock
    private ShorteningRepository repository;

    @Mock
    private Encoder encoder;

    @Mock
    private ShorteningProperties properties;

    @InjectMocks
    private Shortener shortener;

    @BeforeEach
    void setUp() {
        lenient().when(properties.getBaseUrl()).thenReturn("http://short.ly");
    }

    @Test
    void testGetShorteningExists() {
        String code = "abc";
        Shortening shortening = new Shortening("http://example.com");
        shortening.setCode(code);
        when(repository.findByCode(code)).thenReturn(Optional.of(shortening));

        Optional<ShorteningOutDto> result = shortener.getShortening(code);

        assertTrue(result.isPresent());
        assertEquals("http://example.com", result.get().getOriginalUrl());
        assertEquals("http://short.ly/abc", result.get().getShortenedUrl());
        assertEquals(code, result.get().getCode());
    }

    @Test
    void testGetShorteningNotExists() {
        String code = "none";
        when(repository.findByCode(code)).thenReturn(Optional.empty());

        Optional<ShorteningOutDto> result = shortener.getShortening(code);

        assertFalse(result.isPresent());
    }

    @Test
    void testDefineShorteningExists() {
        String url = "http://example.com";
        Shortening shortening = new Shortening(url);
        shortening.setCode("abc");
        when(repository.findByUrl(url)).thenReturn(Optional.of(shortening));

        ShorteningOutDto result = shortener.defineShortening(new ShorteningInpDto(url));

        assertEquals(url, result.getOriginalUrl());
        assertEquals("http://short.ly/abc", result.getShortenedUrl());
        assertEquals("abc", result.getCode());
        verify(repository, never()).save(any());
    }

    @Test
    void testDefineShorteningNew() {
        String url = "http://new.com";
        Shortening shorteningWithoutCode = new Shortening(url);
        // Using reflection or manually setting ID if possible, but mock will do.
        Shortening shorteningWithId = mock(Shortening.class);
        when(shorteningWithId.getId()).thenReturn(1L);
        when(shorteningWithId.getUrl()).thenReturn(url);
        
        when(repository.findByUrl(url)).thenReturn(Optional.empty());
        when(repository.save(any(Shortening.class))).thenReturn(shorteningWithId);
        when(encoder.encodeInt(1L)).thenReturn("1");
        
        // Mock the second save call to return the same object (now with code set)
        when(shorteningWithId.getCode()).thenReturn("1");

        ShorteningOutDto result = shortener.defineShortening(new ShorteningInpDto(url));

        assertEquals(url, result.getOriginalUrl());
        assertEquals("http://short.ly/1", result.getShortenedUrl());
        assertEquals("1", result.getCode());
        verify(repository, times(2)).save(any());
        verify(shorteningWithId).setCode("1");
    }
}
