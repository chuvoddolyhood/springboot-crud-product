package com.example.springboot_crud_product.Service.Interface;

import java.util.List;
import java.util.Map;

import com.example.springboot_crud_product.DTO.ProductDTO;
import com.example.springboot_crud_product.Entity.Product;

public interface ProductService {

	public List<ProductDTO> getAll();

	public ProductDTO getById(Long id);

	public ProductDTO addItem(ProductDTO p);

	public void deleteItem(Long id);

	public ProductDTO updateItem(Long id, ProductDTO p);

	public ProductDTO updateProductPartially(Long id, Map<String, Object> fields);

	public Product toEntity(ProductDTO dto);

	public ProductDTO toDto(Product entity);
}
