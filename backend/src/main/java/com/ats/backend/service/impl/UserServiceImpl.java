package com.ats.backend.service.impl;

import com.ats.backend.dto.UserDto;
import com.ats.backend.entity.User;
import com.ats.backend.exception.ResourceNotFoundException;
import com.ats.backend.mapper.UserMapper;
import com.ats.backend.repository.UserRepository;
import com.ats.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    // @RequiredArgsConstructor from Lombok automatically injects this! No autowired needed.
    private final UserRepository userRepository;

    @Override
    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        return UserMapper.mapToUserDto(user);
    }
}