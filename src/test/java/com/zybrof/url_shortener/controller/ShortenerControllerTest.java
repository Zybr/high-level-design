package com.zybrof.url_shortener.controller;

import com.zybrof.url_shortener.dto.ShorteningInpDto;
import com.zybrof.url_shortener.dto.ShorteningOutDto;
import com.zybrof.url_shortener.service.Shortener;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ShortenerController.class)
class ShortenerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Shortener shortener;

    @Test
    void testCreateShortening() throws Exception {
        ShorteningOutDto outDto = new ShorteningOutDto("http://example.com", "http://short.ly/abc", "abc");
        when(shortener.defineShortening(any(ShorteningInpDto.class))).thenReturn(outDto);

        mockMvc.perform(post("/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"url\":\"http://example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originalUrl").value("http://example.com"))
                .andExpect(jsonPath("$.shortenedUrl").value("http://short.ly/abc"))
                .andExpect(jsonPath("$.code").value("abc"));
    }

    @Test
    void testRedirect() throws Exception {
        ShorteningOutDto outDto = new ShorteningOutDto("http://example.com", "http://short.ly/abc", "abc");
        when(shortener.getShortening("abc")).thenReturn(Optional.of(outDto));

        mockMvc.perform(get("/shorten/abc"))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "http://example.com"));
    }

    @Test
    void testRedirectNotFound() throws Exception {
        when(shortener.getShortening("none")).thenReturn(Optional.empty());

        mockMvc.perform(get("/shorten/none"))
                .andExpect(status().isNotFound());
    }
}
