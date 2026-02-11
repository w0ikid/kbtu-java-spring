package com.example.demo.user.api;

import com.example.demo.user.app.AuthService;
import com.example.demo.user.api.dto.LoginRequest;
import com.example.demo.user.api.dto.LoginResponse;
import com.example.demo.user.domain.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final AuthService authService;
    
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        String token = authService.login(request.email(), request.password());
        
        User user = authService.getUserById(
            authService.getUserIdFromToken(token)
        );
        
        return new LoginResponse(
            token,
            user.getId(),
            user.getEmail(),
            user.getUsername()
        );
    }
}