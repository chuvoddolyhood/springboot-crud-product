package com.example.springboot_crud_product.Service.Interface;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.example.springboot_crud_product.DTO.ProductDTO;
import com.example.springboot_crud_product.Entity.Product;

public interface ProductService {

	public Product toEntity(ProductDTO dto);

	public ProductDTO toDto(Product entity);

	public List<ProductDTO> getAll();

	public ProductDTO getById(Long id);

	public ProductDTO addItem(ProductDTO p);

	public void deleteItem(Long id);

	public ProductDTO updateItem(Long id, ProductDTO p);

	public ProductDTO updateProductPartially(Long id, Map<String, Object> fields);

	public List<ProductDTO> getProductsByMinPrice(Double price);

	public List<ProductDTO> getProductByTitle(String name);

	public List<ProductDTO> getProductByTitle2(String name);

	public Page<ProductDTO> findByNameContaining(String name, int page, int size);

	public Page<ProductDTO> findByPriceBetween(Double priceFrom, Double priceTo, int page, int size);

	public ProductDTO findTopByOrderByPriceDesc();

	public List<Object[]> countProductByPrice();

}
