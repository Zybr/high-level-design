package com.zybrof.url_shortener.src.service;

public class Encoder {
    private final String ALPHABET;
    private final Integer ALB_SIZE;

    public Encoder(String alphabet) {
        this.ALPHABET = alphabet;
        this.ALB_SIZE = alphabet.length();
    }

    public String encodeInt(Long value) {
        if (value == null || value < 0) {
            throw new IllegalArgumentException("Value must be non-negative");
        }

        StringBuilder result = new StringBuilder();

        do {
            int index = Math.toIntExact(value % ALB_SIZE);
            char symbol = ALPHABET.charAt(index);
            result.append(symbol);
            value /= ALB_SIZE;
        } while (value != 0);

        return result.reverse().toString();
    }
}
