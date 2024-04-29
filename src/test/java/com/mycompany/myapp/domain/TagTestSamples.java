package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class TagTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Tag getTagSample1() {
        return new Tag().id(1).name("name1");
    }

    public static Tag getTagSample2() {
        return new Tag().id(2).name("name2");
    }

    public static Tag getTagRandomSampleGenerator() {
        return new Tag().id(intCount.incrementAndGet()).name(UUID.randomUUID().toString());
    }
}
