package com.example.springboot_crud_product.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot_crud_product.DTO.ProductDTO;
import com.example.springboot_crud_product.Service.Interface.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService service;

	@GetMapping
	public List<ProductDTO> getAll() {
		return service.getAll();
	}

	@GetMapping("/{id}")
	public ProductDTO getById(@PathVariable("id") Long id) {
		return service.getById(id);
	}

	@PostMapping
	public ProductDTO addItem(@Validated @RequestBody ProductDTO dto) {
		return service.addItem(dto);
	}

	@DeleteMapping("/{id}")
	public void deleteItem(@PathVariable("id") Long id) {
		service.deleteItem(id);
	}

	@PutMapping("/{id}")
	public ProductDTO updateItem(@PathVariable("id") Long id, @RequestBody ProductDTO dto) {
		return service.updateItem(id, dto);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<ProductDTO> updateProductPartially(@PathVariable("id") Long id,
			@RequestBody Map<String, Object> fields) {

		ProductDTO updatedProduct = service.updateProductPartially(id, fields);

		return ResponseEntity.ok(updatedProduct);
	}

}
