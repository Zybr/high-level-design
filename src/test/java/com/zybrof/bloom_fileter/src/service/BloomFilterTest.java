package com.zybrof.bloom_fileter.src.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BloomFilterTest {

    @Test
    void testBasicOperations() {
        // Simple filter with 1000 bits and 5 hash functions
        BloomFilter<String> filter = new BloomFilter<>(1000, 5);
        
        filter.add("hello");
        filter.add("world");
        
        assertTrue(filter.mightContain("hello"));
        assertTrue(filter.mightContain("world"));
        assertFalse(filter.mightContain("non-existent"));
    }

    @Test
    void testFalsePositiveRate() {
        int expectedElements = 10000;
        int size = 100000; // 10 bits per element
        int k = 7;
        double expectedRate = 0.01; 
        BloomFilter<Integer> filter = new BloomFilter<>(size, k);
        
        for (int i = 0; i < expectedElements; i++) {
            filter.add(i);
        }
        
        int falsePositives = 0;
        int tests = 100000;
        for (int i = expectedElements; i < expectedElements + tests; i++) {
            if (filter.mightContain(i)) {
                falsePositives++;
            }
        }
        
        double actualRate = (double) falsePositives / tests;
        System.out.println("Actual false positive rate: " + actualRate);
        assertTrue(actualRate < expectedRate * 2, "False positive rate too high: " + actualRate);
    }
}
