package com.studyspring.todocrud.service;

import com.studyspring.todocrud.dto.AuthResponseDto;
import com.studyspring.todocrud.dto.UserLoginDto;
import com.studyspring.todocrud.dto.UserRegistrationDto;
import com.studyspring.todocrud.exception.AppException;
import com.studyspring.todocrud.model.User;
import com.studyspring.todocrud.repo.UserRepo;
import com.studyspring.todocrud.utils.jwt.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public AuthResponseDto register(UserRegistrationDto userRegistrationDto) {
        if (userRepo.existsByUsername(userRegistrationDto.getUsername())) {
            throw new AppException("Username already exists", HttpStatus.BAD_REQUEST);
        }

        if (userRepo.existsByEmail(userRegistrationDto.getEmail())) {
            throw new AppException("Email already exists", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setUsername(userRegistrationDto.getUsername());
        user.setEmail(userRegistrationDto.getEmail());
        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));

        User savedUser = userRepo.save(user);
        String token = jwtUtil.generateToken(savedUser.getUsername());

        return new AuthResponseDto(token, savedUser.getUsername(), savedUser.getEmail());
    }

    @Override
    public AuthResponseDto login(UserLoginDto userLoginDto) {
        User user = userRepo.findByUsername(userLoginDto.getUsername())
                .orElseThrow(() -> new AppException("Invalid username or password", HttpStatus.UNAUTHORIZED));

        if (!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            throw new AppException("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }

        String token = jwtUtil.generateToken(user.getUsername());
        return new AuthResponseDto(token, user.getUsername(), user.getEmail());
    }
}