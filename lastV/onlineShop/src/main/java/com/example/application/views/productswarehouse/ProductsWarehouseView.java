package com.example.application.views.productswarehouse;

import com.example.application.data.entities.Article;
import com.example.application.data.services.ArticleService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

@PageTitle("Products Warehouse")
@Route(value = "products-warehouse", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@Uses(Icon.class)
public class ProductsWarehouseView extends Div {

    private Grid<Article> grid;

    private Filters filters;

    private ArticleService productService;
    public ProductsWarehouseView(ArticleService productService) {
        this.productService = productService;
        setSizeFull();
        addClassNames("products-warehouse-view");
        filters = new Filters(this::refreshGrid);
        VerticalLayout layout = new VerticalLayout(filters, createGrid());
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        add(layout);
    }



    public static class Filters extends Div implements Specification<Article> {

        private final TextField name = new TextField("Article Name");

        private final NumberField quantity = new NumberField("Article Quantity");
        private final NumberField price = new NumberField("Article Price");
//        private final DatePicker startDate = new DatePicker("Date of Birth");
//        private final DatePicker endDate = new DatePicker();
////        private final MultiSelectComboBox<> occupations = new MultiSelectComboBox<>("Occupation");
//

        public Filters(Runnable onSearch) {

            setWidthFull();
            addClassName("filter-layout");
            addClassNames(LumoUtility.Padding.Horizontal.LARGE, LumoUtility.Padding.Vertical.MEDIUM,
                    LumoUtility.BoxSizing.BORDER);
            name.setPlaceholder("Product name");
            // Action buttons
            Button resetBtn = new Button("Reset");
            resetBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            resetBtn.addClickListener(e -> {
                name.clear();
                quantity.clear();
                price.clear();
                onSearch.run();
            });
            Button searchBtn = new Button("Search");
            searchBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            searchBtn.addClickListener(e -> onSearch.run());

            Div actions = new Div(resetBtn, searchBtn);
            actions.addClassName(LumoUtility.Gap.SMALL);
            actions.addClassName("actions");

            add(name,actions);
        }



        @Override
        public Predicate toPredicate(Root<com.example.application.data.entities.Article> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            List<Predicate> predicates = new ArrayList<>();

            if (!name.isEmpty()) {
                String lowerCaseFilter = name.getValue().toLowerCase();
                Predicate articleNameMatch = criteriaBuilder.like(criteriaBuilder.lower(root.get("articleName")), "%" +
                        lowerCaseFilter + "%");
                predicates.add(articleNameMatch);
            }
            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        }
    }

    private Component createGrid() {
        grid = new Grid<>(Article.class, false);
        grid.addColumn("articleName").setAutoWidth(true);
        grid.addColumn("articlePrice").setAutoWidth(true);
        grid.addColumn("articleQuantity").setAutoWidth(true);
        grid.setItems(query -> productService.list(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)),
                filters).stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);

        return grid;
    }

    private void refreshGrid() {
        grid.getDataProvider().refreshAll();
    }

}
