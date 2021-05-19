package com.example.application.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.example.application.backend.model.Warehouse;
import com.example.application.backend.repository.WarehouseRepository;

@Service
public class WarehouseService {
	private static final Logger LOGGER = Logger.getLogger(WarehouseService.class.getName());
	private WarehouseRepository warehouseRepository;
	
	public WarehouseService(WarehouseRepository warehouseRepository) {
		this.warehouseRepository = warehouseRepository;
	}
	
	public List<Warehouse> findAll() {
        return warehouseRepository.findAll();
    }

	public Optional<Warehouse> findById(long id) {
        return warehouseRepository.findById(id);
    }
	
    public long count() {
        return warehouseRepository.count();
    }

    public void delete(Warehouse warehouse) {
    	warehouseRepository.delete(warehouse);
    }

    public void save(Warehouse warehouse) {
        if (warehouse == null) {
            LOGGER.log(Level.SEVERE,
                "Warehouse is null. Are you sure you have connected your form to the application?");
            return;
        }
        warehouseRepository.save(warehouse);
    } 
}
