package com.example.application.views.productmaster;

import com.example.application.backend.model.Product;
import com.example.application.backend.model.Stock;
import com.example.application.backend.model.Warehouse;
import com.example.application.backend.service.ProductService;
import com.example.application.backend.service.StockService;
import com.example.application.backend.service.WarehouseService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.binder.BindingValidationStatus;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.main.MainView;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Route(value = "productMaster", layout = MainView.class)
@PageTitle("Product Master")
public class ProductMasterView extends VerticalLayout {


    // Inyecto servicios
    private ProductService productService;
    private StockService stockService;
    private WarehouseService warehouseService;

    public ProductMasterView(ProductService productService, StockService stockService, WarehouseService warehouseService) {
        this.productService = productService;
        this.stockService = stockService;
        this.warehouseService = warehouseService;

        addClassName("product-master-view");


        // Agrego los botones y el grid
        add(CreaLayoutHorizBotones(), crearLayoutGrid());


    }


    public HorizontalLayout crearLayoutGrid(){

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

        grid.addComponentColumn(product -> createUpdateButton(product)).setHeader("");

        grid.addComponentColumn(product -> createRemoveButton(product)).setHeader("");


        layoutGrid.add(grid);

        return layoutGrid;
    }




    private HorizontalLayout CreaLayoutHorizBotones() {
        // Creo LayoutHorizontal
        HorizontalLayout layoutBotones = new HorizontalLayout();
        layoutBotones.setPadding(true);

        // Boton "add Stock"
        Button buttonAddStock = new Button("Add Stock");


        buttonAddStock.addClickListener(funcion ->{

            Dialog dialog = formFlotanteProduct(null);
//            ProductMasterForm dialog = new ProductMasterForm(warehouseService);
            dialog.open();
        });


        // Botón "Remove Stock"
        Button buttonRefreshStock = new Button("Refresh Stock");
        buttonRefreshStock.addClickListener(funcion ->{
            // Refresca la página
            UI.getCurrent().getPage().reload();
        });





        buttonRefreshStock.getElement().getStyle().set("margin-left", "auto");

        layoutBotones.add(buttonAddStock, buttonRefreshStock);


        return layoutBotones;

    }


    private Dialog formFlotanteProduct(Product product){
        //////// Creación del DIÁLOGO

        // Creo dialog flotante
        Dialog dialog = new Dialog();

        FormLayout layoutWithBinder = new FormLayout();
        Binder<Product> binder = new Binder<>();

        // The object that will be edited
        Product productBeingEdited = new Product();



            // Campo WAREHOUSE
        ComboBox<Warehouse> warehouse = new ComboBox<>();
        warehouse.setLabel("Warehouse");
        warehouse.setItemLabelGenerator(Warehouse::getName);
        warehouse.setItems(warehouseService.findAll());
        if (product != null)  warehouse.setValue(product.getWarehouse());

        // Campo FAMILY
        ComboBox<Product.Family> family = new ComboBox<>();
        family.setItems(Product.Family.values());
        if (product != null) family.setValue(product.getFamily());

            // Campo ACTIVE
        Checkbox checkboxActive = new Checkbox();
        if (product != null)  checkboxActive.setValue(product.isActive());


        // Campo NOMBRE
        TextField name = new TextField();
        name.setValueChangeMode(ValueChangeMode.EAGER);
        if (product != null) name.setValue(product.getName());

            // Campo DESCRIPTION
        TextField description = new TextField();
        description.setValueChangeMode(ValueChangeMode.EAGER);
        if (product != null) description.setValue(product.getDescription());

            // Campo PRICE
        NumberField price = new NumberField("Price");
        price.setValueChangeMode(ValueChangeMode.EAGER);
        price.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
        price.setPrefixComponent(new Icon(VaadinIcon.EURO));
        if (product != null) price.setValue(product.getPrice());



            Label infoLabel = new Label();
        NativeButton save = new NativeButton("Save");
        NativeButton reset = new NativeButton("Reset");


        layoutWithBinder.addFormItem(warehouse, "Warehouse");
        layoutWithBinder.addFormItem(family, "Family");
        layoutWithBinder.addFormItem(checkboxActive, "Active");
        layoutWithBinder.addFormItem(name, "Name");
        layoutWithBinder.addFormItem(description, "Description");
        layoutWithBinder.addFormItem(price, "Price");

        // Button bar
        HorizontalLayout actions = new HorizontalLayout();
        actions.add(save, reset);
        save.getStyle().set("marginRight", "10px");


        name.setRequiredIndicatorVisible(true);
        binder.forField(name)
                .asRequired("Name is required")
                .bind(Product::getName, Product::setName);

        price.setRequiredIndicatorVisible(true);
        binder.forField(price).asRequired("Price is required")
                .bind(Product::getPrice, Product::setPrice);


        description.setRequiredIndicatorVisible(true);
        binder.forField(description).asRequired("Description is required")
                .bind(Product::getDescription, Product::setDescription);

        binder.forField(family).asRequired("Family is required")
                .bind(Product::getFamily, Product::setFamily);


        // Click listeners for the buttons
        save.addClickListener(event -> {

            if (binder.writeBeanIfValid(productBeingEdited)) {

                productBeingEdited.setWarehouse(warehouse.getValue());
                productBeingEdited.setActive(checkboxActive.getValue());

                infoLabel.setText("Saved bean values: " + productBeingEdited.toString());

                productService.save(productBeingEdited);
                UI.getCurrent().getPage().reload();

            } else {
                BinderValidationStatus<Product> validate = binder.validate();
                String errorText = validate.getFieldValidationStatuses()
                        .stream().filter(BindingValidationStatus::isError)
                        .map(BindingValidationStatus::getMessage)
                        .map(Optional::get).distinct()
                        .collect(Collectors.joining(", "));
                infoLabel.setText("There are errors: " + errorText);
            }
        });
        reset.addClickListener(event -> {
            // clear fields by setting null
            binder.readBean(null);
            infoLabel.setText("");
        });


        dialog.add(layoutWithBinder, actions, infoLabel);

        return dialog;


    }




    private Button createUpdateButton(Product product) {

        Button button = new Button("Update");
        button.addClickListener(funcion ->{
            System.out.println(product.toString());
            Dialog dialog = formFlotanteProduct(product);
            dialog.open();
        });






        return button;

    }

    private Button createRemoveButton(Product product) {

        Button button = new Button("Remove");

        button.addClickListener(funcion -> {
            System.out.println("Pulsado botón Remove , product:" + product.getName());
            List<Stock> listadoStock = product.getStocks();

            // Borro los Stocks asociados al producto
            for (Stock elementoStock :
                    listadoStock) {
                stockService.delete(elementoStock);
            }
            // Dejo el producto sin stock
            product.setStocks(null);

            // Finalmente borro el producto
            productService.delete(product);

            // Refresca la página
            UI.getCurrent().getPage().reload();

        });

        return button;
    }



}
