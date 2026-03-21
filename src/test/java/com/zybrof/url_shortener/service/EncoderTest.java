package com.zybrof.url_shortener.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EncoderTest {
    private final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final Encoder encoder = new Encoder(ALPHABET);

    @Test
    void testEncodeZero() {
        assertEquals("0", encoder.encodeInt(0L));
    }

    @Test
    void testEncodeOne() {
        assertEquals("1", encoder.encodeInt(1L));
    }

    @Test
    void testEncodeSixtyOne() {
        assertEquals("Z", encoder.encodeInt(61L));
    }

    @Test
    void testEncodeSixtyTwo() {
        assertEquals("10", encoder.encodeInt(62L));
    }

    @Test
    void testEncodeLargeValue() {
        // 62 * 62 + 0 * 62 + 61 = 3844 + 61 = 3905 -> 10Z
        assertEquals("10Z", encoder.encodeInt(3905L));
    }

    @Test
    void testEncodeNull() {
        assertThrows(IllegalArgumentException.class, () -> encoder.encodeInt(null));
    }

    @Test
    void testEncodeNegative() {
        assertThrows(IllegalArgumentException.class, () -> encoder.encodeInt(-1L));
    }
}
