package com.example.application.backend.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.example.application.backend.model.Stock;
import com.example.application.backend.repository.StockRepository;

@Service
public class StockService {
	private static final Logger LOGGER = Logger.getLogger(StockService.class.getName());
	private StockRepository stockRepository;
	
	public StockService(StockRepository stockRepository) {
		this.stockRepository = stockRepository;
	}
	
	public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    public long count() {
        return stockRepository.count();
    }

    public void delete(Stock stock) {
    	stockRepository.delete(stock);
    }
    
    public void save(Stock stock) {
        if (stock == null) {
            LOGGER.log(Level.SEVERE,
                "Stock is null. Are you sure you have connected your form to the application?");
            return;
        }
        stockRepository.save(stock);
    }   
}
