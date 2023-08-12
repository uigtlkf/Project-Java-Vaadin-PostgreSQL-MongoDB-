package com.example.application.data.services;


import com.example.application.data.entities.Article;
import com.example.application.data.repositories.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockService {

    private final ArticleRepository articleRepository;

    public StockService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Transactional
    public void reduceStock(Article article, int quantity) {
        if (hasEnoughStock(article, quantity)) {
            var article1 = articleRepository.findArticleByArticleName(article.getArticleName()).orElse(null);
            article1.setArticleQuantity(article1.getArticleQuantity() - quantity);
            articleRepository.save(article1);
        } else {
            throw new IllegalArgumentException("Not enough stock for article " + article.getArticleName());
        }
    }

    public boolean hasEnoughStock(Article article, int quantity) {
        return article.getArticleQuantity() >= quantity;
    }
}

