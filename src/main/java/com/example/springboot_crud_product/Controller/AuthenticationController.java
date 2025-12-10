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
@RequestMapping("/auth")
public class AuthenticationController {

	@GetMapping("/public")
	public String authPublic() {
		return "Hello public authentication";
	}
		
	@GetMapping("/secure")
	public String secureUser() {
		return "Hello secure User authentication";
	}
	
	@GetMapping("/admin/dashboard")
	public String adminDashboard() {
		return "Hello adminDashboard";
	}
	
	@GetMapping("/user/profile")
	public String userProfile() {
		return "Hello userProfile";
	}

}
