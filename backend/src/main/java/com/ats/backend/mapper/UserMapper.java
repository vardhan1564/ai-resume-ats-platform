package com.ats.backend.mapper;

import com.ats.backend.dto.UserDto;
import com.ats.backend.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static UserDto mapToUserDto(User user) {
        if (user == null) {
            return null;
        }
        
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .build();
    }
}