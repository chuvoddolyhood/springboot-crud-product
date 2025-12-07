package com.example.springboot_crud_product.Service.Implementation;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.example.springboot_crud_product.DTO.ProductDTO;
import com.example.springboot_crud_product.Entity.Product;
import com.example.springboot_crud_product.Repository.ProductRepository;
import com.example.springboot_crud_product.Service.Interface.ProductService;

@Service
public class ProductServiceImp implements ProductService {

	@Autowired
	private ProductRepository repo;

	@Autowired
	private ModelMapper mapper;

	@Override
	public Product toEntity(ProductDTO dto) {
		return mapper.map(dto, Product.class);
	}

	@Override
	public ProductDTO toDto(Product entity) {
		return mapper.map(entity, ProductDTO.class);
	}

	@Override
	public List<ProductDTO> getAll() {
//		return repo.findAll().stream().map(p -> mapper.map(p, ProductDTO.class)).toList();
		return repo.findAll().stream().map(this::toDto).toList();
	}

	@Override
	public ProductDTO getById(Long id) {
		Product p = repo.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id = " + id));
		return toDto(p);
	}

	@Override
	public ProductDTO addItem(ProductDTO p) {
		if (p.getPrice() < 0) {
			throw new IllegalArgumentException("Price cannot be negative");
		}
		Product saved = repo.save(toEntity(p));
		return toDto(saved);
	}

	@Override
	public void deleteItem(Long id) {
		Product p = repo.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id = " + id));
		repo.deleteById(p.getId());
	}

	@Override
	public ProductDTO updateItem(Long id, ProductDTO p) {
		ProductDTO old = getById(id);
		old.setName(p.getName());

		if (p.getPrice() < 0) {
			throw new IllegalArgumentException("Price cannot be negative");
		}
		old.setPrice(p.getPrice());

		Product entity = repo.save(toEntity(old));

		return toDto(entity);
	}

	@Override
	public ProductDTO updateProductPartially(Long id, Map<String, Object> fields) {
		ProductDTO product = getById(id);

		fields.forEach((key, value) -> {
			Field field = ReflectionUtils.findField(ProductDTO.class, key);
			if (field == null) {
				throw new IllegalArgumentException("Field " + key + " is not valid");
			}
			field.setAccessible(true);
			ReflectionUtils.setField(field, product, value);
		});

		Product entity = repo.save(toEntity(product));

		return toDto(entity);
	}

	@Override
	public List<ProductDTO> getProductsByMinPrice(Double price) {
		List<Product> products = repo.getProductsByMinPrice(price);

		return products.stream().map(this::toDto).toList();
	}

	@Override
	public List<ProductDTO> getProductByTitle(String name) {
		List<Product> products = repo.getProductByTitle(name);
		return products.stream().map(this::toDto).toList();
	}

	@Override
	public List<ProductDTO> getProductByTitle2(String name) {
		List<Product> products = repo.getProductByTitle2(name);
		return products.stream().map(this::toDto).toList();
	}

	@Override
	public Page<ProductDTO> findByNameContaining(String keyword, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("price").descending());
		Page<Product> products = repo.findByNameContainingIgnoreCase(keyword, pageable);
		return products.map(this::toDto);
	}

	@Override
	public Page<ProductDTO> findByPriceBetween(Double priceFrom, Double priceTo, int page, int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("price").ascending());
		Page<Product> products = repo.findByPriceBetween(priceFrom, priceTo, pageable);
		return products.map(this::toDto);
	}

	@Override
	public ProductDTO findTopByOrderByPriceDesc() {
		return toDto(repo.findTopByOrderByPriceDesc());
	}

	@Override
	public List<Object[]> countProductByPrice() {
		return repo.countProductByPrice();
	}

}
