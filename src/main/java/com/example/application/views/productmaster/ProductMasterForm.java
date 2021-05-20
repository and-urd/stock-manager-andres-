package com.example.application.views.productmaster;


import com.example.application.backend.model.Product;
import com.example.application.backend.model.Warehouse;
import com.example.application.backend.service.WarehouseService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;

import java.util.List;

public class ProductMasterForm extends Dialog {

    public static enum DIALOG_RESULT {SAVE, CANCEL};
    private DIALOG_RESULT dialog_result;
    private WarehouseService warehouseService;
    private Product product;
    private Binder<Product> productBinder = new BeanValidationBinder<Product>(Product.class);
    private FormLayout formLayout;

    private ComboBox<Warehouse>warehouse;
    private Checkbox active;
    private TextField name;
    private TextField description;
    private ComboBox<Product.Family>family;
    private NumberField price;


    public ProductMasterForm(WarehouseService warehouseService){
        super();
        this.warehouseService=warehouseService;
        add(createTittle(), createFormLayout(), new Hr(), createToolbarLyout());
        productBinder.bindInstanceFields(this);
    }

    public DIALOG_RESULT getDialog_result() {
        return this.dialog_result;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
        productBinder.readBean(product);
    }

    private List<Warehouse> getWarehouses() {
        return warehouseService.findAll();
    }

    public Component createTittle(){
        return new H3("Product Form");
    }

    /*
    FORM
     */
    public Component createFormLayout(){
        formLayout = new FormLayout();
        formLayout.setWidthFull();
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("1px",1),
                new FormLayout.ResponsiveStep("60px",2),
                new FormLayout.ResponsiveStep("1px",3));

        /*
        ROW 1
         */
        HorizontalLayout row01 = new HorizontalLayout();
        row01.setPadding(false);
        row01.setMargin(false);

        warehouse = new ComboBox<Warehouse>();
        warehouse.setId("warehouse");
        warehouse.setItemLabelGenerator(Warehouse::getName);
        warehouse.setLabel("Warehouse");
        warehouse.setItems(getWarehouses());
        warehouse.setAutofocus(true);
        warehouse.setWidth("300px");
        productBinder.forField(warehouse).asRequired("Warehouse is required");

        active = new Checkbox();
        active.setId("active");
        active.setLabel("Active");
        active.getElement().getStyle().set("margin-left","auto");

        row01.add(warehouse,active);
        row01.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        formLayout.setColspan(row01, 2);

        /*
        ROW 2
         */
        HorizontalLayout row02 = new HorizontalLayout();
        row01.setPadding(false);
        row01.setMargin(false);

        name = new TextField();
        name.setId("name");
        name.setLabel("Name");
        name.setWidth("300px");
        productBinder.forField(name).withNullRepresentation("").asRequired("Name is required");

        row02.add(name);

        /*
        ROW 3
         */
        HorizontalLayout row03 = new HorizontalLayout();

        description = new TextField();
        description.setId("description");
        description.setLabel("Description");

        row03.add(description);
        formLayout.setColspan(row03, 2);

        /*
        ROW 4
         */
        HorizontalLayout row04 = new HorizontalLayout();
        row04.setPadding(false);
        row04.setMargin(false);

        family = new ComboBox<Product.Family>();
        family.setId("family");
        family.setLabel("Family");
        family.setItems(Product.Family.values());
        productBinder.forField(family).asRequired("Family is rquired");

        price = new NumberField();
        price.setId("price");
        price.setLabel("Price");
        price.addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
        price.setPrefixComponent(new Icon(VaadinIcon.EURO));
        productBinder.forField(price).asRequired("Price is required");

        row04.add(family,price);

        formLayout.add(row01,row02,row03,row04);
        return formLayout;
    }

    private Component createToolbarLyout(){
        Button saveButton = new Button("Confirm", event ->{
            this.dialog_result = DIALOG_RESULT.SAVE;
            if(productBinder.writeBeanIfValid(product))
                close();
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.addClickShortcut(Key.ENTER).listenOn(this);
        saveButton.getElement().getStyle().set("margin-left", "auto");

        Button cancelButton = new Button("Cancel", event -> {
            this.dialog_result = DIALOG_RESULT.CANCEL;
            close();
        });

        HorizontalLayout formToolBar = new HorizontalLayout(saveButton,cancelButton);
        formToolBar.setWidthFull();
        formToolBar.getElement().getStyle().set("padding-top", "30px");

        return formToolBar;
    }

}

