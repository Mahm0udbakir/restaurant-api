package com.restaurant.restaurant_api.auth;

import com.restaurant.restaurant_api.dto.AuthRequest;
import com.restaurant.restaurant_api.dto.AuthResponse;
import com.restaurant.restaurant_api.security.JwtUtil;
import com.restaurant.restaurant_api.user.UserRepository;
import com.restaurant.restaurant_api.user.User;
import com.restaurant.restaurant_api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

/**
 * Authentication controller exposes /api/auth/register and /api/auth/login.
 *
 * Comments are added to help explain what endpoints do and why they are needed.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest req) {
        // Create a new user. In a real app you would validate and check duplicates.
        User u = userService.register(req.username(), req.password());
        return ResponseEntity.ok(u.getUsername());
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {
        // Authenticate the user using AuthenticationManager which delegates to
        // configured auth providers. Here we perform a simple username/password check.
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.username(), req.password()));
        // If authentication succeeds, build a JWT. We include user's roles as a claim.
        var user = userRepository.findByUsername(req.username()).orElseThrow();
        var roles = user.getRoles().stream().map(Enum::name).collect(Collectors.toList());
        String token = jwtUtil.generateToken(user.getUsername(), roles);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
