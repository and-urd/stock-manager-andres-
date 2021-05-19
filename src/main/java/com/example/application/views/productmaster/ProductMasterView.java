package com.example.application.views.productmaster;

import com.example.application.backend.model.Product;
import com.example.application.backend.service.ProductService;
import com.example.application.backend.service.WarehouseService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.main.MainView;

@Route(value = "productMaster", layout = MainView.class)
@PageTitle("Product Master")
public class ProductMasterView extends VerticalLayout {


    // Inyecto servicios
    private ProductService productService;
    private WarehouseService warehouseService;
    public ProductMasterView(ProductService productService, WarehouseService warehouseService) {
        addClassName("product-master-view");

        HorizontalLayout layoutGrid = new HorizontalLayout();

        Grid<Product> grid = new Grid<>(Product.class);
        grid.setItems(productService.findAll());

        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);

        grid.setColumns("warehouse.name", "name", "description", "price");


        grid.getColumnByKey("warehouse.name")
                                                    .setHeader("Warehouse")
                                                    .setFooter(new Label("Total:" + String.valueOf( productService.count())));

        grid.addColumn(
                new ComponentRenderer<>(
                        product -> {
                            Checkbox checkbox = new Checkbox();
                            checkbox.setValue( product.isActive() );
                            checkbox.isReadOnly();
                            return checkbox;
                        }
                )
        ).setHeader("Active").setKey("Active");


        grid.addComponentColumn(item -> createUpdateButton()).setHeader("");

        grid.addComponentColumn(item -> createRemoveButton()).setHeader("");


        add(CreaLayoutHorizBotones(), grid);


    }








    private Button createUpdateButton() {

        Button button = new Button("Update");
        return button;
    }

    private Button createRemoveButton() {

        Button button = new Button("Remove");

        return button;
    }









    private HorizontalLayout CreaLayoutHorizBotones() {
        // Creo LayoutHorizontal
        HorizontalLayout layoutBotones = new HorizontalLayout();
        layoutBotones.setPadding(true);

        // Creo y agrego los botones
        Button buttonAddStock = new Button("Add Stock");
        Button buttonRefreshStock = new Button("Refresh Stock");
        buttonRefreshStock.getElement().getStyle().set("margin-left", "auto");

        layoutBotones.add(buttonAddStock, buttonRefreshStock);


        return layoutBotones;

    }




}
