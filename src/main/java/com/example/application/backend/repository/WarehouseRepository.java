package com.example.application.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.application.backend.model.Warehouse;



public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}
