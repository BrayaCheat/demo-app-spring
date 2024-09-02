package com.example.bookStore.Controllers;

import com.example.bookStore.Models.DTO.Response.ApiResponse;
import com.example.bookStore.Models.User;
import com.example.bookStore.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/api/v1/user")
@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<User>> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ApiResponse<>(
                                "Get current user",
                                currentUser,
                                HttpStatus.OK,
                                LocalDateTime.now()
                        )
                );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> allUsers() {
        List<User> users = userService.listUsers();
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ApiResponse<>(
                                "List all users",
                                users,
                                HttpStatus.OK,
                                LocalDateTime.now()
                        )
                );
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse<Long>> countUserLength() {
        Long userLength = userService.countUserLength();
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ApiResponse<>(
                                "Count all users",
                                userLength,
                                HttpStatus.OK,
                                LocalDateTime.now()
                        )
                );
    }
}
