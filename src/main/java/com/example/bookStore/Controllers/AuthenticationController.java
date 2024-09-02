package com.example.bookStore.Controllers;

import com.example.bookStore.Models.DTO.Request.LoginUserDto;
import com.example.bookStore.Models.DTO.Request.RegisterUserDto;
import com.example.bookStore.Models.DTO.Response.ApiResponse;
import com.example.bookStore.Models.DTO.Response.LoginResponse;
import com.example.bookStore.Models.User;
import com.example.bookStore.Services.AuthenticationService;
import com.example.bookStore.Services.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> register(@Validated @RequestBody RegisterUserDto registerUserDto) {
        try {
            User registeredUser = authenticationService.signup(registerUserDto);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(
                            new ApiResponse<>(
                                    "Register Successful",
                                    registeredUser,
                                    HttpStatus.OK,
                                    LocalDateTime.now()
                            )
                    );
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body(
                            new ApiResponse<>(
                                    ex.getMessage(),
                                    null,
                                    HttpStatus.NOT_ACCEPTABLE,
                                    LocalDateTime.now()
                            )
                    );
        }

    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> authenticate(@Validated @RequestBody LoginUserDto loginUserDto) {
        try{
            User authenticatedUser = authenticationService.authenticate(loginUserDto);
            String jwtToken = jwtService.generateToken(authenticatedUser);
            LoginResponse loginResponse = new LoginResponse()
                    .setToken(jwtToken)
                    .setExpiresIn(jwtService.getExpirationTime());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(
                            new ApiResponse<>(
                                    "Login Successful",
                                    loginResponse,
                                    HttpStatus.OK,
                                    LocalDateTime.now()
                            )
                    );
        }catch(RuntimeException ex){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body(
                            new ApiResponse<>(
                                    ex.getMessage(),
                                    null,
                                    HttpStatus.NOT_ACCEPTABLE,
                                    LocalDateTime.now()
                            )
                    );
        }

    }
}
