package com.example.application.views.orders;

import com.example.application.data.entities.Order;
import com.example.application.data.entities.OrderStatus;
import com.example.application.data.services.ArticleService;
import com.example.application.data.services.OrderService;
import com.example.application.data.services.StockService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.List;

@PageTitle("Orders")
@Route(value = "orders", layout = MainLayout.class)
@AnonymousAllowed
public class OrdersView extends Div implements AfterNavigationObserver {

    private final OrderService service;
    private final StockService articleService;
    Grid<Order> grid = new Grid<>();


    public OrdersView(OrderService service, StockService articleService) {
        this.service = service;
        this.articleService = articleService;
        addClassName("orders-view");
        setSizeFull();
        grid.setHeight("100%");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER, GridVariant.LUMO_NO_ROW_BORDERS);
        List<Order> orders = service.findAll();
        grid.setItems(orders);
        grid.addComponentColumn(this::createCard);
        add(grid);
    }

    private HorizontalLayout createCard(Order order) {
        HorizontalLayout card = new HorizontalLayout();
        card.addClassName("card");
        card.setSpacing(false);
        card.getThemeList().add("spacing-s");

        Image image = new Image();
        image.setSrc("https://cdn-icons-png.flaticon.com/512/1559/1559869.png");
        VerticalLayout description = new VerticalLayout();
        description.addClassName("description");
        description.setSpacing(false);
        description.setPadding(false);

        HorizontalLayout header = new HorizontalLayout();
        header.addClassName("header");
        header.setSpacing(false);
        header.getThemeList().add("spacing-s");

        Span name = new Span(order.getCustomer().getLastname());
        name.addClassName("name");
        Span date = new Span(order.getOrderDate());
        date.addClassName("date");
        header.add(name, date);

        Button completeButton = new Button("Complete");
        completeButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        completeButton.addClickListener(event -> {
            order.setStatus(OrderStatus.DELIVERED.toString());
            service.save(order);
            completeButton.setVisible(false);
            grid.addClassName("lightGreenGrid");
            grid.getDataProvider().refreshItem(order);
        });
        Span post = new Span(order.getOrderSummary());
        post.addClassName("post");
        Span status = new Span(order.getStatus());
        status.addClassName("post");
        Span providerSpan = new Span(order.getDeliveryProvider() == null ? "" : order.getDeliveryProvider());
        providerSpan.addClassName("provider");
        HorizontalLayout actions = new HorizontalLayout();
        actions.addClassName("actions");
        actions.setSpacing(false);
        actions.getThemeList().add("spacing-s");
        Button cancelButton = new Button("Cancel");
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancelButton.addClickListener(event -> {
            order.setStatus(OrderStatus.CANCELLED.toString());
            service.save(order);
            grid.getDataProvider().refreshItem(order);
        });
        var actions2 = new HorizontalLayout();
        Button deliveryButton  = new Button("Set to Delivery");
        deliveryButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        deliveryButton.addClickListener(event -> {
            Dialog dialog = new Dialog();
            Select<String> select = new Select<>();
            select.setLabel("Delivery Provider");
            select.setItems("UPC", "DHL", "Austrian Post"); // Set your provider names here
            select.addValueChangeListener(e -> {
                if (e.getValue() != null) {
                    order.setDeliveryProvider("Delivery by: "+ e.getValue());
                    order.setStatus(OrderStatus.IN_PROGRESS.toString());
                    service.save(order);
                    for (var item : order.getArticles()) {
                        articleService.reduceStock(item,item.getArticleQuantity());
                    }

                    updateButtons(order, actions2, completeButton, deliveryButton,cancelButton);
                    dialog.close();
                    grid.getDataProvider().refreshItem(order);
                }
            });
            dialog.add(select);
            dialog.open();
        });

        updateButtons(order, actions2, completeButton, deliveryButton,cancelButton);

        description.add(header, post,status,providerSpan, actions);
        card.add(image, description, actions2);
        actions.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        return card;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {


    }
    private void updateButtons(Order order, HorizontalLayout buttonContainer, Button completeButton, Button deliveryButton,Button cancelButton) {
        buttonContainer.removeAll();

        if (OrderStatus.IN_PROGRESS.toString().equals(order.getStatus())) {
            buttonContainer.add(completeButton);
        } else if (!OrderStatus.DELIVERED.toString().equals(order.getStatus())) {
            buttonContainer.add(deliveryButton);
        }
        buttonContainer.add(cancelButton);
    }


}
