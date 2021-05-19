package com.example.application.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.application.backend.model.Product;



public interface ProductRepository extends JpaRepository<Product, Long> {

}
