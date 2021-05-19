package com.example.application.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.application.backend.model.Stock;



public interface StockRepository extends JpaRepository<Stock, Long> {
}
