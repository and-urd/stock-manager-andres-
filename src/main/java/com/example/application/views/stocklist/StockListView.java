package com.example.application.views.stocklist;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.backend.model.Product;
import com.example.application.backend.model.Warehouse;
import com.example.application.backend.repository.ProductRepository;
import com.example.application.backend.service.ProductService;
import com.example.application.backend.service.WarehouseService;
import com.example.application.views.main.MainView;

@Route(value = "stockList", layout = MainView.class)
@PageTitle("Stock List")
public class StockListView extends VerticalLayout {

	private  ProductService productService;
	private WarehouseService warehouseService;
	
    public StockListView(ProductService productService, WarehouseService warehouseService) {
        
		
		addClassName("stock-list-view");
                
        
        
        HorizontalLayout layoutGrid = new HorizontalLayout();
        
        
        
        
        Grid<Product> grid = new Grid<>(Product.class);
        grid.setItems(productService.findAll());
        

        
        
        
        
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER,
                GridVariant.LUMO_NO_ROW_BORDERS, GridVariant.LUMO_ROW_STRIPES);

        // The Grid<>(Person.class) sorts the properties and in order to
        // reorder the properties we use the 'setColumns' method.
//        grid.setColumns("warehouse", "name", "description", "family", "price", "active");
        
        
//        addColumn((Person person) ->{ return person.getGroup().getGroupName()},new TextRenderer());
//        grid.addColumn(Product::getWarehouse).setHeader("Warehouse");
//        grid.addColumn()
//        grid.addColumn(Product::getName).setHeader("Name");
//        grid.addColumn(Product::getDescription).setHeader("Description");
        
        grid.setColumns("warehouse.name", "name", "description", "price");
        
        
//        grid.addColumn(product -> {
//            Checkbox checkbox = new Checkbox();
//            checkbox.setValue( product.isActive() );
//            return checkbox;
//        }).setHeader("active");
        
        grid.addColumn(
                new ComponentRenderer<>(
                        product -> {
                            Checkbox checkbox = new Checkbox();
                            checkbox.setValue( product.isActive() );                            
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
