package com.example.application.views.productmaster;

import com.example.application.backend.model.Product;
import com.example.application.backend.model.Stock;
import com.example.application.backend.model.Warehouse;
import com.example.application.backend.service.ProductService;
import com.example.application.backend.service.StockService;
import com.example.application.backend.service.WarehouseService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.binder.BindingValidationStatus;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
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

        grid.addComponentColumn(item -> createUpdateButton()).setHeader("");

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

            Dialog dialog = formFlotanteProduct();
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


    private Dialog formFlotanteProduct(){
        //////// Creación del DIÁLOGO

        // Creo dialog flotante
        Dialog dialog = new Dialog();

        FormLayout layoutWithBinder = new FormLayout();
        Binder<Product> binder = new Binder<>();

        // The object that will be edited
        Product productBeingEdited = new Product();


        // Campo WAREHOUSE
        Select<Warehouse> warehouseSelect = new Select<>();
        warehouseSelect.setLabel("Warehouse");
        warehouseSelect.setItemLabelGenerator(Warehouse::getName);
        warehouseSelect.setItems(warehouseService.findAll());

        // Campo NOMBRE
        TextField name = new TextField();
        name.setValueChangeMode(ValueChangeMode.EAGER);

        // Campo DESCRIPTION
        TextField description = new TextField();
        description.setValueChangeMode(ValueChangeMode.EAGER);

        // Campo PRICE

        BigDecimalField price = new BigDecimalField("Price");
        price.setValueChangeMode(ValueChangeMode.EAGER);
        price.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
        price.setPrefixComponent(new Icon(VaadinIcon.EURO));



        Label infoLabel = new Label();
        NativeButton save = new NativeButton("Save");
        NativeButton reset = new NativeButton("Reset");


        layoutWithBinder.addFormItem(warehouseSelect, "Warehouse");
        layoutWithBinder.addFormItem(name, "Name");
        layoutWithBinder.addFormItem(description, "Description");
        layoutWithBinder.addFormItem(price, "Price");

        // Button bar
        HorizontalLayout actions = new HorizontalLayout();
        actions.add(save, reset);
        save.getStyle().set("marginRight", "10px");
//
//        // Both phone and email cannot be empty
//        SerializablePredicate<String> phoneOrEmailPredicate = value -> !phone
//                .getValue().trim().isEmpty()
//                || !email.getValue().trim().isEmpty();
//
//        // E-mail and phone have specific validators
//        Binder.Binding<Product, String> emailBinding = binder.forField(price)
//                .withNullRepresentation("")
//                .withValidator(phoneOrEmailPredicate,
//                        "Please specify your email")
//                .withValidator(new EmailValidator("Incorrect email address"))
//                .bind(Contact::getEmail, Contact::setEmail);
//
//        Binding<Contact, String> phoneBinding = binder.forField(description)
//                .withValidator(phoneOrEmailPredicate,
//                        "Please specify your phone")
//                .bind(Contact::getPhone, Contact::setPhone);
//
//        // Trigger cross-field validation when the other field is changed
//        price.addValueChangeListener(event -> phoneBinding.validate());
//        description.addValueChangeListener(event -> emailBinding.validate());
//
        // Address is a required field
        name.setRequiredIndicatorVisible(true);
        binder.forField(name)
                .withValidator(new StringLengthValidator(
                        "Please add the address", 1, null))
                .bind(Product::getName, Product::setName);

        // Click listeners for the buttons
        save.addClickListener(event -> {

            if (binder.writeBeanIfValid(productBeingEdited)) {

                productBeingEdited.setWarehouse(warehouseSelect.getValue());

                infoLabel.setText("Saved bean values: " + productBeingEdited.toString());

//                productService.save(productBeingEdited);

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




    private Button createUpdateButton() {

        Button button = new Button("Update");
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
