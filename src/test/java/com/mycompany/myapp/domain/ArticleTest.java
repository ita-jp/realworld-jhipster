package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.ArticleTestSamples.*;
import static com.mycompany.myapp.domain.TagTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ArticleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Article.class);
        Article article1 = getArticleSample1();
        Article article2 = new Article();
        assertThat(article1).isNotEqualTo(article2);

        article2.setId(article1.getId());
        assertThat(article1).isEqualTo(article2);

        article2 = getArticleSample2();
        assertThat(article1).isNotEqualTo(article2);
    }

    @Test
    void tagTest() throws Exception {
        Article article = getArticleRandomSampleGenerator();
        Tag tagBack = getTagRandomSampleGenerator();

        article.addTag(tagBack);
        assertThat(article.getTags()).containsOnly(tagBack);

        article.removeTag(tagBack);
        assertThat(article.getTags()).doesNotContain(tagBack);

        article.tags(new HashSet<>(Set.of(tagBack)));
        assertThat(article.getTags()).containsOnly(tagBack);

        article.setTags(new HashSet<>());
        assertThat(article.getTags()).doesNotContain(tagBack);
    }
}
