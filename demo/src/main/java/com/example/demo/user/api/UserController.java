package com.example.demo.user.api;

import com.example.demo.user.app.UserService;
import com.example.demo.user.api.dto.UpdateRequest;
import com.example.demo.user.api.dto.UserCreateRequest;
import com.example.demo.user.api.dto.UserResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/users")
public class UserController {
    
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse registerUser(@RequestBody UserCreateRequest request) {
        var user = userService.registerUser(
            request.username(),
            request.email(),
            request.password()
        );
        return new UserResponse(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getCreatedAt()
        );
    }
    
    @GetMapping("/{id}")
    public UserResponse getUser(@PathVariable Long id) {
        var user = userService.get(id);
        return new UserResponse(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getCreatedAt()
        );
    }

    @PutMapping("/username")
    public UserResponse updateUsername(@RequestBody UpdateRequest request) {
        var user = userService.updateUsername(request.username());
        return new UserResponse(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getCreatedAt()
        );
    }
}
