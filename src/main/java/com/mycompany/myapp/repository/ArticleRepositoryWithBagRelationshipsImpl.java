package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Article;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class ArticleRepositoryWithBagRelationshipsImpl implements ArticleRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String ARTICLES_PARAMETER = "articles";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Article> fetchBagRelationships(Optional<Article> article) {
        return article.map(this::fetchTags);
    }

    @Override
    public Page<Article> fetchBagRelationships(Page<Article> articles) {
        return new PageImpl<>(fetchBagRelationships(articles.getContent()), articles.getPageable(), articles.getTotalElements());
    }

    @Override
    public List<Article> fetchBagRelationships(List<Article> articles) {
        return Optional.of(articles).map(this::fetchTags).orElse(Collections.emptyList());
    }

    Article fetchTags(Article result) {
        return entityManager
            .createQuery("select article from Article article left join fetch article.tags where article.id = :id", Article.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Article> fetchTags(List<Article> articles) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, articles.size()).forEach(index -> order.put(articles.get(index).getId(), index));
        List<Article> result = entityManager
            .createQuery("select article from Article article left join fetch article.tags where article in :articles", Article.class)
            .setParameter(ARTICLES_PARAMETER, articles)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
