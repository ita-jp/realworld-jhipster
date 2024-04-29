package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ArticleTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Article getArticleSample1() {
        return new Article().id(1L).slug("slug1").title("title1").description("description1").body("body1");
    }

    public static Article getArticleSample2() {
        return new Article().id(2L).slug("slug2").title("title2").description("description2").body("body2");
    }

    public static Article getArticleRandomSampleGenerator() {
        return new Article()
            .id(longCount.incrementAndGet())
            .slug(UUID.randomUUID().toString())
            .title(UUID.randomUUID().toString())
            .description(UUID.randomUUID().toString())
            .body(UUID.randomUUID().toString());
    }
}
