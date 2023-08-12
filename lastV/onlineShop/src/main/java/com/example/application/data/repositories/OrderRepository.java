package com.example.application.data.repositories;


import com.example.application.data.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

/*    @Query("SELECT o.customer AS country, COUNT(o) AS orderCount " +
            "FROM Order o " +
            "WHERE o.article.id = :articleId " +
            "GROUP BY o.customer.country " +
            "ORDER BY orderCount DESC")
    List<CountryOrderCount> findTopCountriesByArticle(@Param("articleId") int articleId, Pageable pageable);
*/
}


