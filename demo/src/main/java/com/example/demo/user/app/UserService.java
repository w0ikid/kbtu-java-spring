package com.example.demo.user.app;

import com.example.demo.user.domain.User;
import com.example.demo.user.infra.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.user.domain.exception.UserAlreadyExistsException;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User registerUser(String username, String email, String password) {
        try {
            User user = new User(username, email, password);
            return userRepository.save(user);
        } catch (Exception e) {
            throw new UserAlreadyExistsException("User with the same username or email already exists");
        }
    }

    public User get(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
