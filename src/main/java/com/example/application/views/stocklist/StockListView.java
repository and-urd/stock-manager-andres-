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


    public StockListView() {

		addClassName("stock-list-view");

    }


    
    


}
