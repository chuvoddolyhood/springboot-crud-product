package com.example.springboot_crud_product.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springboot_crud_product.Entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
