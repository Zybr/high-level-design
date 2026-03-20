package com.zybrof.rate_limiter.sliding_window_log;

import com.zybrof.rate_limiter.src.sliding_window_log.SlidingWindowLogRateLimiter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SlidingWindowLogRateLimiterTest {

    private static class MockSlidingWindowLogRateLimiter extends SlidingWindowLogRateLimiter {
        private long currentTime = 0;

        public MockSlidingWindowLogRateLimiter(int capacity, int interval) {
            super(capacity, interval);
        }

        @Override
        protected long getTime() {
            return currentTime;
        }

        public void advanceTime(long seconds) {
            currentTime += seconds;
        }
    }

    @Test
    void testRateLimitLogic() {
        // Capacity: 2, Interval: 10 seconds
        MockSlidingWindowLogRateLimiter limiter = new MockSlidingWindowLogRateLimiter(2, 10);

        // First call - should be allowed
        assertTrue(limiter.isAllowed(), "First call should be allowed");
        // Second call - should be allowed
        assertTrue(limiter.isAllowed(), "Second call should be allowed");
        // Third call - should be rejected
        assertFalse(limiter.isAllowed(), "Third call should be rejected (capacity: 2)");

        // Advance time by 5 seconds (still in window)
        limiter.advanceTime(5);
        assertFalse(limiter.isAllowed(), "Should still be rejected after 5s");

        // Advance time by another 6 seconds (total 11s)
        // The first call at t=0 should have expired by t=11 (expiration = 11 - 10 = 1).
        // t=0 <= 1 is true, so it's expired.
        limiter.advanceTime(6);
        assertTrue(limiter.isAllowed(), "Should be allowed again after 11s");
    }
}
