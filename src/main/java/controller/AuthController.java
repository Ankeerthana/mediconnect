package com.mediconnect.controller;

import com.mediconnect.config.JwtUtil;
import com.mediconnect.entity.User;
import com.mediconnect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> req) {
        if (userRepository.existsByEmail(req.get("email")))
            return ResponseEntity.badRequest().body("Email already exists");

        User user = new User();
        user.setEmail(req.get("email"));
        user.setPassword(passwordEncoder.encode(req.get("password")));
        user.setName(req.get("name"));
        user.setPhone(req.get("phone"));
        user.setRole(User.Role.valueOf(req.get("role").toUpperCase()));
        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> req) {
        return userRepository.findByEmail(req.get("email"))
                .filter(u -> passwordEncoder.matches(req.get("password"), u.getPassword()))
                .map(u -> ResponseEntity.ok(Map.of(
                        "token", jwtUtil.generateToken(u.getEmail()),
                        "role", u.getRole(),
                        "name", u.getName())))
                .orElse(ResponseEntity.badRequest().body(null));
    }
}