package com.example.application.views.productmaster;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.example.application.views.main.MainView;

@Route(value = "productMaster", layout = MainView.class)
@PageTitle("Product Master")
public class ProductMasterView extends Div {

    public ProductMasterView() {
        addClassName("product-master-view");
        add(new Text("Content placeholder"));
    }

}
