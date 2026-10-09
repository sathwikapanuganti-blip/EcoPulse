package com.ecopulse.controller;

import com.ecopulse.dto.ApiResponse;
import com.ecopulse.dto.AuthRequest;
import com.ecopulse.dto.AuthResponse;
import com.ecopulse.entity.User;
import com.ecopulse.repository.UserRepository;
import com.ecopulse.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody AuthRequest.Register req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Username is already taken!"));
        }
        if (userRepository.existsByEmail(req.getEmail())) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Email address is already in use!"));
        }

        User user = new User(
                req.getUsername(),
                req.getEmail(),
                passwordEncoder.encode(req.getPassword()),
                req.getFullName(),
                "ROLE_USER",
                req.getCommunityId() != null ? req.getCommunityId() : 1L
        );
        User saved = userRepository.save(user);

        String token = jwtUtil.generateToken(saved.getUsername(), saved.getRole());
        AuthResponse response = new AuthResponse(
                token, saved.getId(), saved.getUsername(), saved.getEmail(), saved.getFullName(), saved.getRole(), saved.getCommunityId()
        );
        return ResponseEntity.ok(ApiResponse.ok("User registered successfully", response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody AuthRequest.Login req) {
        User user = userRepository.findByUsername(req.getUsername())
                .or(() -> userRepository.findByEmail(req.getUsername()))
                .orElse(null);

        if (user == null || !passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            // Check for quick demo login fallback
            if ("admin".equalsIgnoreCase(req.getUsername()) && "admin123".equals(req.getPassword())) {
                String token = jwtUtil.generateToken("admin", "ROLE_ADMIN");
                return ResponseEntity.ok(ApiResponse.ok("Demo Admin Login Successful",
                        new AuthResponse(token, 2L, "admin", "admin@ecopulse.org", "Community Green Warden", "ROLE_ADMIN", 1L)));
            }
            if ("ecouser".equalsIgnoreCase(req.getUsername()) && "ecopulse123".equals(req.getPassword())) {
                String token = jwtUtil.generateToken("ecouser", "ROLE_USER");
                return ResponseEntity.ok(ApiResponse.ok("Demo Citizen Login Successful",
                        new AuthResponse(token, 1L, "ecouser", "citizen@ecopulse.org", "Eco Citizen", "ROLE_USER", 1L)));
            }
            return ResponseEntity.badRequest().body(ApiResponse.error("Invalid username/email or password!"));
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());
        AuthResponse response = new AuthResponse(
                token, user.getId(), user.getUsername(), user.getEmail(), user.getFullName(), user.getRole(), user.getCommunityId()
        );
        return ResponseEntity.ok(ApiResponse.ok("Login successful", response));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout() {
        return ResponseEntity.ok(ApiResponse.ok("User logged out successfully", null));
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<AuthResponse>> getProfile(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) Long userId,
            org.springframework.security.core.Authentication authentication) {
        String lookupUsername = username;
        if (lookupUsername == null && authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName())) {
            lookupUsername = authentication.getName();
        }

        User user = null;
        if (lookupUsername != null) {
            user = userRepository.findByUsername(lookupUsername).orElse(null);
        } else if (userId != null) {
            user = userRepository.findById(userId).orElse(null);
        } else {
            user = userRepository.findByUsername("ecouser").orElse(null);
        }

        if (user == null) {
            return ResponseEntity.badRequest().body(ApiResponse.error("User profile not found"));
        }

        AuthResponse resp = new AuthResponse(
                null, user.getId(), user.getUsername(), user.getEmail(), user.getFullName(), user.getRole(), user.getCommunityId()
        );
        return ResponseEntity.ok(ApiResponse.ok("Profile retrieved successfully", resp));
    }
}
