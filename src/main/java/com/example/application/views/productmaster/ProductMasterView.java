package com.example.application.views.productmaster;

import com.example.application.backend.model.Product;
import com.example.application.backend.model.Stock;
import com.example.application.backend.service.ProductService;
import com.example.application.backend.service.StockService;
import com.example.application.backend.service.WarehouseService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.main.MainView;

import java.util.List;

@Route(value = "productMaster", layout = MainView.class)
@PageTitle("Product Master")
public class ProductMasterView extends VerticalLayout {


    // Inyecto servicios
    private ProductService productService;
    private StockService stockService;

    public ProductMasterView(ProductService productService, StockService stockService) {
        this.productService = productService;
        this.stockService = stockService;

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
                            checkbox.setEnabled(false);
                            return checkbox;
                        }
                )
        ).setHeader("Active").setKey("Active");


        grid.addComponentColumn(item -> createUpdateButton()).setHeader("");

        grid.addComponentColumn(product -> createRemoveButton(product)).setHeader("");


        layoutGrid.add(grid);
        add(CreaLayoutHorizBotones(), layoutGrid);


    }








    private Button createUpdateButton() {

        Button button = new Button("Update");
        return button;
    }

    private Button createRemoveButton(Product product) {

        Button button = new Button("Remove");

        button.addClickListener(funcion -> {
            System.out.println("Pulsado botón Remove , product:" + product.getName());
            List<Stock> listadoStock = product.getStocks();

            for (Stock elementoStock :
                    listadoStock) {
                stockService.delete(elementoStock);
            }
            product.setStocks(null);

            productService.delete(product);

        });

        return button;
    }









    private HorizontalLayout CreaLayoutHorizBotones() {
        // Creo LayoutHorizontal
        HorizontalLayout layoutBotones = new HorizontalLayout();
        layoutBotones.setPadding(true);

        // Creo y agrego los botones
        Button buttonAddStock = new Button("Add Stock");

        Button buttonRefreshStock = new Button("Refresh Stock");
        buttonRefreshStock.addClickListener(funcion ->{
            UI.getCurrent().getPage().reload();
        });





        buttonRefreshStock.getElement().getStyle().set("margin-left", "auto");

        layoutBotones.add(buttonAddStock, buttonRefreshStock);


        return layoutBotones;

    }




}
