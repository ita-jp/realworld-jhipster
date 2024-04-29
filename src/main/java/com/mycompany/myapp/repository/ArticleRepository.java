package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Article;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Article entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("select article from Article article where article.user.login = ?#{authentication.name}")
    List<Article> findByUserIsCurrentUser();
}
