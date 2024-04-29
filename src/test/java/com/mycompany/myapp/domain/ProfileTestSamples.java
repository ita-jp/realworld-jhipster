package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProfileTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Profile getProfileSample1() {
        return new Profile().id(1L).bio("bio1").image("image1");
    }

    public static Profile getProfileSample2() {
        return new Profile().id(2L).bio("bio2").image("image2");
    }

    public static Profile getProfileRandomSampleGenerator() {
        return new Profile().id(longCount.incrementAndGet()).bio(UUID.randomUUID().toString()).image(UUID.randomUUID().toString());
    }
}
