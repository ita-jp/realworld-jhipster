package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.ArticleTestSamples.*;
import static com.mycompany.myapp.domain.TagTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TagTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tag.class);
        Tag tag1 = getTagSample1();
        Tag tag2 = new Tag();
        assertThat(tag1).isNotEqualTo(tag2);

        tag2.setId(tag1.getId());
        assertThat(tag1).isEqualTo(tag2);

        tag2 = getTagSample2();
        assertThat(tag1).isNotEqualTo(tag2);
    }

    @Test
    void articleTest() throws Exception {
        Tag tag = getTagRandomSampleGenerator();
        Article articleBack = getArticleRandomSampleGenerator();

        tag.addArticle(articleBack);
        assertThat(tag.getArticles()).containsOnly(articleBack);
        assertThat(articleBack.getTags()).containsOnly(tag);

        tag.removeArticle(articleBack);
        assertThat(tag.getArticles()).doesNotContain(articleBack);
        assertThat(articleBack.getTags()).doesNotContain(tag);

        tag.articles(new HashSet<>(Set.of(articleBack)));
        assertThat(tag.getArticles()).containsOnly(articleBack);
        assertThat(articleBack.getTags()).containsOnly(tag);

        tag.setArticles(new HashSet<>());
        assertThat(tag.getArticles()).doesNotContain(articleBack);
        assertThat(articleBack.getTags()).doesNotContain(tag);
    }
}
