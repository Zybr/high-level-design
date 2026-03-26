package com.zybrof.rate_limiter.src.bucket;

import com.zybrof.rate_limiter.src.bucket.BucketRateLimiter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BucketRateLimiterTest {

    private static class MockBucketRateLimiter extends BucketRateLimiter {
        private long currentTime = 0;

        public MockBucketRateLimiter(int capacity, int interval) {
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
    void testRefillBug() {
        // Capacity: 10, Refill interval: 10 seconds
        MockBucketRateLimiter limiter = new MockBucketRateLimiter(10, 10);

        // Consume all tokens
        for (int i = 0; i < 10; i++) {
            assertTrue(limiter.isAllowed(), "Token " + i + " should be allowed");
        }
        assertFalse(limiter.isAllowed(), "Bucket should be empty");

        // Advance time by 6 seconds (not enough for a full refill interval)
        limiter.advanceTime(6);
        assertFalse(limiter.isAllowed(), "Refill should not have happened yet (6/10 < 1)");
        
        // Advance time by another 6 seconds (total 12 seconds)
        // Correct implementation should have refilled 1 token by now.
        // Buggy implementation resets 'lastTime' to 6 at the previous call.
        // So at 12s, 'period' will be 12-6 = 6, which is still < 10. Refill fails again.
        limiter.advanceTime(6);
        assertTrue(limiter.isAllowed(), "Refill should have happened by now (12/10 >= 1)");
    }
}
