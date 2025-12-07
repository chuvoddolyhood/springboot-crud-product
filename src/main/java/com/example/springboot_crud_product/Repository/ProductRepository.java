package com.example.springboot_crud_product.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.springboot_crud_product.Entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query("SELECT p FROM Product p WHERE p.price > :price")
	List<Product> getProductsByMinPrice(@Param("price") Double price);

	@Query(value = "SELECT * FROM products WHERE LOWER(name) LIKE '%' || LOWER(?1) || '%'", nativeQuery = true)
	List<Product> getProductByTitle(String name);

	@Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword%")
	List<Product> getProductByTitle2(@Param("keyword") String keyword);

	Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

	Page<Product> findByPriceBetween(Double priceFrom, Double priceTo, Pageable pageable);

	Product findTopByOrderByPriceDesc();

	@Query("SELECT p.price, COUNT(p.price) FROM Product p GROUP BY p.price")
	List<Object[]> countProductByPrice();
}
