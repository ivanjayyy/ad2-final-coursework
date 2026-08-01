package lk.ijse.userservice.controller;

import jakarta.validation.Valid;
import lk.ijse.userservice.dto.LoginRequest;
import lk.ijse.userservice.exception.ResourceNotFoundException;
import lk.ijse.userservice.model.User;
import lk.ijse.userservice.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepo userRepo;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@Valid @RequestBody User user) {
        if (userRepo.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("A user with this email already exists");
        }
        user.setId(null);
        return userRepo.save(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        User user = userRepo.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("No user found with this email"));
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "userId", user.getId(),
                "name", user.getName(),
                "role", user.getRole()
        ));
    }
}
