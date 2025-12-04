package com.example.springboot_crud_product.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.springboot_crud_product.Entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query("SELECT p FROM Product p WHERE p.price > :price")
	List<Product> getProductsByMinPrice(@Param("price") Double price);
}
