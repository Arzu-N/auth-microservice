package org.example.authmicroservice.mapper;

import org.example.authmicroservice.dao.entity.UserEntity;
import org.example.authmicroservice.dto.LoginDto;
import org.example.authmicroservice.dto.RegisterDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserEntity registerDtoToEntity(RegisterDto dto) {
        return UserEntity.builder()
                .userName(dto.getUserName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(dto.getRole())
                .build();
    }

}
