package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class UserExtendedTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static UserExtended getUserExtendedSample1() {
        return new UserExtended().id(1L);
    }

    public static UserExtended getUserExtendedSample2() {
        return new UserExtended().id(2L);
    }

    public static UserExtended getUserExtendedRandomSampleGenerator() {
        return new UserExtended().id(longCount.incrementAndGet());
    }
}
