package com.ats.backend.service;

import com.ats.backend.dto.UserDto;

public interface UserService {
    UserDto getUserById(Long id);
    UserDto getUserByEmail(String email);
}