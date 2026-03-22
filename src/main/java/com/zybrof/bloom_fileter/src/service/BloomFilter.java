package com.zybrof.bloom_fileter.src.service;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;

/**
 * Bloom Filter is a probabilistic data structure used to test whether an element is a member of a set.
 * It may return false positives but never false negatives.
 *
 * @param <T> Type of items to be stored
 */
public class BloomFilter<T> {
    private final BitSet commonBits;
    private final int commonSize;
    private final int itemSize;

    /**
     * @param commonSize Number of bits in the filter
     * @param itemSize   Number of hash functions to use - max number of bits for an item
     */
    public BloomFilter(int commonSize, int itemSize) {
        commonBits = new BitSet(commonSize);
        this.commonSize = commonSize;
        this.itemSize = itemSize;
    }

    /**
     * Add item to the filter
     */
    public void add(T item) {
        if (item == null) return;

        ArrayList<Integer> itemBits = getItemBits(item); // Get item bits
        itemBits.forEach(commonBits::set); // Add item bits to filter
    }

    public boolean mightContain(T item) {
        ArrayList<Integer> itemBits = getItemBits(item); // Get item bits
        return hasAllBits(itemBits); // Check if all item bits are set
    }

    /**
     * Get bits for an item
     */
    private ArrayList<Integer> getItemBits(T item) {
        int hasCode = item.hashCode();
        Random random = new Random(hasCode);
        ArrayList<Integer> bits = new ArrayList<>();

        for (int i = 0; i < itemSize; i++) {
            int bit = random.nextInt(commonSize);
            bits.add(bit);
        }

        return bits;
    }

    /**
     * Check if all bits are set in the filter
     */
    private boolean hasAllBits(ArrayList<Integer> bits) {
        return bits.stream().allMatch(commonBits::get);
    }
}
