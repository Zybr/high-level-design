package com.zybrof.rate_limiter.src.bucket;

import com.zybrof.rate_limiter.src.RateLimiter;

public class BucketRateLimiter implements RateLimiter {
    private final int capacity; // Max tokens
    private final int interval; // Refill interval (seconds)
    private int count = 0; // Current tokens
    private long lastTime = getTime(); // Last time of the refill (seconds)

    public BucketRateLimiter(int capacity, int interval) {
        this.capacity = capacity;
        this.interval = interval;
        this.count = capacity;
    }

    @Override
    public synchronized boolean isAllowed() {
        refill();

        if (isEmpty()) {
            return false;
        }

        decrease();
        return true;
    }

    private void refill() {
        long now = getTime();
        long period = now - lastTime;
        int periodCount = (int) (period / (long) interval);

        this.count = Math.min(
                count + periodCount, // Count with refill
                capacity // Max count
        );

        this.lastTime += (long) periodCount * interval;
    }

    private boolean isEmpty() {
        return this.count == 0;
    }

    private void decrease() {
        this.count -= 1;
    }

    protected long getTime() {
        return System.currentTimeMillis() / 1_000L;
    }
}
