package com.example.springboot_crud_product.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springboot_crud_product.Entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

	List<RefreshToken> findByToken(String token);
	
	void deleteByUserIdAndDeviceId(Long id, String deviceId);

}
