package com.example.application.views.home;


import com.example.application.data.services.DBFiller;
import com.example.application.data.services.MigrationService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import jakarta.annotation.security.RolesAllowed;

@PageTitle("Home")
@Route(value = "", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@Uses(Icon.class)
public class HomeView extends Composite<VerticalLayout> {

    private final DBFiller dbFiller;
    private final MigrationService migrationService;
    private HorizontalLayout layoutRow = new HorizontalLayout();

    private Button buttonPrimary = new Button();

    private Button buttonSecondary = new Button();

    private VerticalLayout layoutColumn2 = new VerticalLayout();

    public HomeView(DBFiller dbFiller, MigrationService migrationService) {
        this.dbFiller = dbFiller;
        this.migrationService = migrationService;
        getContent().setHeightFull();
        getContent().setWidthFull();
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidthFull();
        buttonPrimary.setText("Fill Database");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonSecondary.setText("Migrate Database");
        getContent().setFlexGrow(1.0, layoutColumn2);
        buttonPrimary.addClickListener( event -> {
           dbFiller.fillDB();
        });
        buttonSecondary.addClickListener( event -> {
            migrationService.migrateToNoSQL();
        });
        layoutColumn2.setWidthFull();
        getContent().add(layoutRow);
        layoutRow.add(buttonPrimary);
        layoutRow.add(buttonSecondary);
        getContent().add(layoutColumn2);
    }
}