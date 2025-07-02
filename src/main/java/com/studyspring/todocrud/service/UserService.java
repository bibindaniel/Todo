package com.studyspring.todocrud.service;

import com.studyspring.todocrud.dto.AuthResponseDto;
import com.studyspring.todocrud.dto.UserLoginDto;
import com.studyspring.todocrud.dto.UserRegistrationDto;

public interface UserService {
    AuthResponseDto register(UserRegistrationDto userRegistrationDto);
    AuthResponseDto login(UserLoginDto userLoginDto);
}