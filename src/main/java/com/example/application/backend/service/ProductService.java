package com.example.application.backend.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.example.application.backend.model.Product;
import com.example.application.backend.repository.ProductRepository;

@Service
public class ProductService {
	private static final Logger LOGGER = Logger.getLogger(ProductService.class.getName());
	private ProductRepository productRepository;
	
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	
	public List<Product> findAll() {
        return productRepository.findAll();
    }

    public long count() {
        return productRepository.count();
    }

    public void delete(Product product) {
    	productRepository.delete(product);
    }

    public void save(Product product) {
        if (product == null) {
            LOGGER.log(Level.SEVERE,
                "Product is null. Are you sure you have connected your form to the application?");
            return;
        }
        productRepository.save(product);
    }    
}
