package com.example.springboot_crud_product.Service.Implementation;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.springboot_crud_product.DTO.AuthResponse;
import com.example.springboot_crud_product.DTO.LoginRequest;
import com.example.springboot_crud_product.DTO.RegisterRequest;
import com.example.springboot_crud_product.Entity.RefreshToken;
import com.example.springboot_crud_product.Entity.User;
import com.example.springboot_crud_product.Repository.RefreshTokenRepository;
import com.example.springboot_crud_product.Repository.UserRepository;
import com.example.springboot_crud_product.Security.JwtUtil;
import com.example.springboot_crud_product.Service.Interface.AuthService;

import jakarta.transaction.Transactional;

@Service
public class AuthServiceImp implements AuthService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtil jwtUtil;

	@Autowired
	RefreshTokenRepository refreshTokenRepo;

	// LOGIN
	@Override
	@Transactional
	public AuthResponse login(LoginRequest req) {
		User user = userRepo.findByUsername(req.getUsername())
				.orElseThrow(() -> new RuntimeException("User not found"));

		if (!encoder.matches(req.getPassword(), user.getPassword())) {
			throw new RuntimeException("Invalid password");
		}

		String accessToken = jwtUtil.generateToken(user);
		String refreshToken = UUID.randomUUID().toString();
		String deviceId = req.getDeviceId();

		// Delete old refreshToken which has userId and DeviceId
		refreshTokenRepo.deleteByUserIdAndDeviceId(user.getId(), deviceId);

		RefreshToken rt = new RefreshToken();
		rt.setToken(refreshToken);
		rt.setUser(user);
		rt.setDeviceId(req.getDeviceId());
		rt.setExpiryDate(Instant.now().plus(7, ChronoUnit.DAYS));
		rt.setRevoked(false);

		refreshTokenRepo.save(rt);

		return new AuthResponse(accessToken, refreshToken);

	}

	// REGISTER
	@Override
	public void register(RegisterRequest req, String role) {

		// Check username exists
		if (userRepo.existsByUsername(req.getUsername())) {
			throw new RuntimeException("Username already exists");
		}

		// Create user and encode password
		User user = new User();
		user.setUsername(req.getUsername());
		user.setPassword(encoder.encode(req.getPassword()));
		user.setRole(role);

		// Save user
		userRepo.save(user);

	}

//	// Refresh (ROTATION)
//	@Override
//	public AuthResponse refresh(RefreshTokenRequest req) {
//
//		RefreshToken oldToken = refreshTokenRepo.findByTokenAndDeviceId(req.getRefreshToken(), req.getDeviceId())
//				.orElseThrow(() -> new RuntimeException("User not found"));
//
//		if (oldToken.isRevoked()) {
//			refreshTokenRepo.revokeAllByUser(oldToken.getUser());
//			throw new SecurityException("Token reuse detected");
//		}
//
//		if (oldToken.getExpiryDate().isBefore(Instant.now())) {
//			throw new RuntimeException("Refresh token expired");
//		}
//
//		oldToken.setRevoked(true);
//		refreshTokenRepo.save(oldToken);
//
//		RefreshToken newToken = new RefreshToken();
//		newToken.setToken(UUID.randomUUID().toString());
//		newToken.setUser(oldToken.getUser());
//		newToken.setDeviceId(oldToken.getDeviceId());
//		newToken.setExpiryDate(Instant.now().plus(7, ChronoUnit.DAYS));
//		newToken.setRevoked(false);
//
//		refreshTokenRepo.save(newToken);
//
//		String accessToken = jwtUtil.generateToken(oldToken.getUser());
//
//		return new AuthResponse(accessToken, newToken.getToken());
//	}
//
//	@Override
//	public void logout(User user, String deviceId) {
//		refreshTokenRepo.deleteByUserIdAndDeviceId(user.getId(), deviceId);
//
//	}

}
