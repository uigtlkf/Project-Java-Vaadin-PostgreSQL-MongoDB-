package com.example.application.data.controller.order;


import com.example.application.data.controller.order.request.OrderRequest;
import com.example.application.data.entities.Article;
import com.example.application.data.entities.Order;
import com.example.application.data.repositories.ArticleRepository;
import com.example.application.data.repositories.CountryOrderCount;
import com.example.application.data.services.OrderFulfillmentService;
import com.example.application.data.services.OrderService;
import com.example.application.data.services.exceptions.InvalidPaymentInfoException;
import com.example.application.data.services.exceptions.MissingCustomerIdException;
import com.example.application.data.services.exceptions.MissingProductException;
import com.example.application.data.services.exceptions.StockException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderFulfillmentService orderFulfillmentService;
    private final ArticleRepository articleRepository;

    public OrderController(OrderService orderService, OrderFulfillmentService orderFulfillmentService, ArticleRepository articleRepository) {
        this.orderService = orderService;
        this.orderFulfillmentService = orderFulfillmentService;
        this.articleRepository = articleRepository;
    }

   @PostMapping("/place")
    public void placeOrder(@RequestBody OrderRequest orderRequest) throws MissingProductException, InvalidPaymentInfoException, MissingCustomerIdException {
       List<Article> articles = orderRequest.getArticleIds().stream()
               .filter(Objects::nonNull)
               .map(id -> articleRepository.findById(id).orElse(null))
               .collect(Collectors.toList());

       double price = articles.stream()
               .mapToDouble(Article::getArticlePrice)
               .sum();

       orderService.saveOrder(orderRequest.getCustomerId(), orderRequest.getCreditCardNr(),
                orderRequest.getCreditCardName(), orderRequest.getOrderDate(), price, articles);
    }

    @PostMapping("/{orderId}/fulfill")
    public Order fulfillOrder(@PathVariable int orderId) throws StockException {
        return orderFulfillmentService.fulfillOrder(orderId);
    }

    @GetMapping("/report/{articleId}")
    public List<CountryOrderCount> report(@PathVariable int articleId) {
        return orderService.getOrdersByCustomerId(articleId);
    }

}

