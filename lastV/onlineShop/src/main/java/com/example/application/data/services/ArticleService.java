package com.example.application.data.services;

import com.example.application.data.entities.Article;
import com.example.application.data.entities.Order;
import com.example.application.data.repositories.ArticleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final ArticleRepository repository;

    public ArticleService(ArticleRepository repository) {
        this.repository = repository;
    }


    public List<Article> findAll(){
        return repository.findAll();
    }

    public Page<Article> list(Pageable pageable, Specification<Article> filter) {
        return repository.findAll(filter,pageable);
    }



    public void updateStock(Order order) {
    }
}
