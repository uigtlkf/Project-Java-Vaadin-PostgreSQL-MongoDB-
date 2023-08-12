package com.example.application.data.repositories;

import com.example.application.data.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer>, JpaSpecificationExecutor<Article> {
    Optional<Article> findArticleByArticleName(String articleName);
}
