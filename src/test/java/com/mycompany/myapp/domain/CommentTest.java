package com.mycompany.myapp.domain;

import static com.mycompany.myapp.domain.ArticleTestSamples.*;
import static com.mycompany.myapp.domain.CommentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CommentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Comment.class);
        Comment comment1 = getCommentSample1();
        Comment comment2 = new Comment();
        assertThat(comment1).isNotEqualTo(comment2);

        comment2.setId(comment1.getId());
        assertThat(comment1).isEqualTo(comment2);

        comment2 = getCommentSample2();
        assertThat(comment1).isNotEqualTo(comment2);
    }

    @Test
    void articleTest() throws Exception {
        Comment comment = getCommentRandomSampleGenerator();
        Article articleBack = getArticleRandomSampleGenerator();

        comment.setArticle(articleBack);
        assertThat(comment.getArticle()).isEqualTo(articleBack);

        comment.article(null);
        assertThat(comment.getArticle()).isNull();
    }
}
