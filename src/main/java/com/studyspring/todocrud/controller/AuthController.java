package com.studyspring.todocrud.controller;

import com.studyspring.todocrud.dto.AuthResponseDto;
import com.studyspring.todocrud.dto.UserLoginDto;
import com.studyspring.todocrud.dto.UserRegistrationDto;
import com.studyspring.todocrud.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegistrationDto userRegistrationDto) {
        AuthResponseDto response = userService.register(userRegistrationDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDto userLoginDto) {
        AuthResponseDto response = userService.login(userLoginDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}