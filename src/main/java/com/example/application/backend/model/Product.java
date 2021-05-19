package com.example.application.backend.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Product extends AbstractEntity implements Cloneable {
	public enum Family {
		PERISHABLE("Perishable"),
		ELECTRONICS("Electronic"),
	    FASHION("Fashion");

	    public final String label;

	    private Family(String label) {
	        this.label = label;
	    }
	    
	    @Override
		public String toString() {
	    	return label;
	    }
	}

	@ManyToOne
	@JoinColumn(name = "warehouse_id")
	@NotNull(message = "Warehouse is required")
	private Warehouse warehouse;
	
    @NotEmpty(message = "Name is required")
	private String name;
    
	private String description;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Family is required")
	private Family family;
		
	@NotNull(message = "Price is required")
	private Double price;
	
	@NotNull(message = "Active is required")
	private boolean active = true;
	
	@OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
	private List<Stock> stocks = new LinkedList<>();
	  
	public Product() {
	}
	
	public Warehouse getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse) {
		this.warehouse = warehouse;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
    public Family getFamily() {
        return family;
    }
    public void setFamily(Family family) {
        this.family = family;
    }
    
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public List<Stock> getStocks() {
		return stocks;
	}

	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
	}
}
