package com.zybrof.rate_limiter.src.sliding_window_log;

import com.zybrof.rate_limiter.src.RateLimiter;

import java.util.LinkedList;
import java.util.Queue;

public class SlidingWindowLogRateLimiter implements RateLimiter {
    private final int capacity;
    private final int interval;
    private final Queue<Long> logs = new LinkedList<>();

    public SlidingWindowLogRateLimiter(
            int capacity,
            int interval
    ) {
        this.capacity = capacity;
        this.interval = interval;
    }

    @Override
    public synchronized boolean isAllowed() {
        pollExpired();

        if (isFull()) {
            return false;
        }

        push();
        return true;
    }

    private boolean isFull() {
        return logs.size() == capacity;
    }

    private void push() {
        logs.add(getTime());
    }

    private void pollExpired() {
        long expiration = getExpirationTime();

        while (!logs.isEmpty() && logs.peek() <= expiration) {
            logs.poll();
        }
    }

    private long getExpirationTime() {
        return getTime() - (long) interval;
    }

    protected long getTime() {
        return System.currentTimeMillis() / 1_000L;
    }
}
