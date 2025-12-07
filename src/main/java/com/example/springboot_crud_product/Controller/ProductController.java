package com.example.springboot_crud_product.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.springboot_crud_product.DTO.ProductDTO;
import com.example.springboot_crud_product.Service.Interface.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService service;

	@GetMapping("/get-all")
	public List<ProductDTO> getAll() {
		return service.getAll();
	}

	// http://localhost:9090/products/id
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

	/**
	 * Lấy sản phẩm theo gia lon hon gia chi dinh
	 * 
	 * http://localhost:9090/products/min-price
	 * 
	 * @return
	 */
	@GetMapping("/min-price")
	public List<ProductDTO> getProductsByMinPrice(@RequestBody ProductDTO dto) {
		return service.getProductsByMinPrice(dto.getPrice());
	}

	/**
	 * Lấy sản phẩm theo tên
	 * 
	 * http://localhost:9090/products/search-title?q=a
	 * 
	 * @return
	 */
	@GetMapping("/search-title")
	public List<ProductDTO> getProductByTitle(@RequestParam("q") String title) {
		return service.getProductByTitle(title);
	}

	// http://localhost:9090/products/search-title2/a
	@GetMapping("/search-title2/{title}")
	public List<ProductDTO> getProductByTitle2(@PathVariable("title") String title) {
		return service.getProductByTitle2(title);
	}

	/**
	 * Lấy sản phẩm theo tên và sắp xếp theo giá giảm dần
	 * 
	 * http://localhost:9090/products/search?keyword=a&page=0&size=1
	 * 
	 * @return
	 */
	@GetMapping("/search")
	public Page<ProductDTO> findByNameContaining(@RequestParam("keyword") String keyword,
			@RequestParam("page") int page, @RequestParam("size") int size) {

		return service.findByNameContaining(keyword, page, size);
	}

	/**
	 * Tìm sản phẩm theo khoảng giá
	 * 
	 * http://localhost:9090/products/search-ranged-price?priceFrom=1000&priceTo=10000&page=0&size=10
	 * 
	 * @return
	 */
	@GetMapping("/search-ranged-price")
	public Page<ProductDTO> findByPriceBetween(@RequestParam("priceFrom") Double priceFrom,
			@RequestParam("priceTo") Double priceTo, @RequestParam("page") int page, @RequestParam("size") int size) {

		return service.findByPriceBetween(priceFrom, priceTo, page, size);
	}

	/**
	 * Lấy danh sách sản phẩm có giá cao nhất
	 * 
	 * http://localhost:9090/products/search-the-greatest-price
	 * 
	 * @return
	 */
	@GetMapping("/search-the-greatest-price")
	public ProductDTO findTopByOrderByPriceDesc() {
		return service.findTopByOrderByPriceDesc();
	}

	/**
	 * Đếm số lượng sản phẩm theo mức giá (group by)
	 * 
	 * http://localhost:9090/products/count-product-price
	 * 
	 * @return
	 */
	@GetMapping("/count-product-price")
	public List<?> countProductByPrice() {
		return service.countProductByPrice();
	}

}
