package com.example.demo.user.app;

import com.example.demo.user.domain.User;
import com.example.demo.user.infra.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.user.domain.exception.UserAlreadyExistsException;

import com.example.demo.security.SecurityUtils;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User registerUser(String username, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("User with email " + email + " already exists");
        }
        
        if (userRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException("User with username " + username + " already exists");
        }

        
        String hashedPassword = passwordEncoder.encode(password);
        User user = new User(username, hashedPassword, email);
        return userRepository.save(user);
    }

    @Transactional
    public User updateUsername(String newUsername) {
        Long userID = SecurityUtils.currentUserId();

        User user = userRepository.findById(userID)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (userRepository.existsByUsername(newUsername)) {
            throw new UserAlreadyExistsException("User with username " + newUsername + " already exists");
        }

        user.setUsername(newUsername);
        return userRepository.save(user);
        
    }

    public User get(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
