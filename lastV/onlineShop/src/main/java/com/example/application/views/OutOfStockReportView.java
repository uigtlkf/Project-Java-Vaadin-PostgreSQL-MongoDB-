package com.example.application.views;

import com.example.application.data.nosql.entities.OutOfStockReportNoSQL;
import com.example.application.data.services.ReportService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.List;
@PageTitle("Out Of Stock Report")
@Route(value = "out-of-stock-report",layout = MainLayout.class)
@AnonymousAllowed
public class OutOfStockReportView extends VerticalLayout {
    private final ReportService reportService;

    public OutOfStockReportView(ReportService reportService) {
        this.reportService = reportService;
        Grid<OutOfStockReportNoSQL> grid = new Grid<>(OutOfStockReportNoSQL.class);
        grid.removeAllColumns();

        grid.addColumn(OutOfStockReportNoSQL::getId)
                .setHeader("Article Name")
                .setKey("id");

        grid.addColumn(OutOfStockReportNoSQL::getTotalOutOfStock)
                .setHeader("Times out of Stock")
                .setKey("totalOutOfStock");

        add(grid);

        List<OutOfStockReportNoSQL> report = reportService.getOutOfStockReport();
        grid.setItems(report);
    }
}
