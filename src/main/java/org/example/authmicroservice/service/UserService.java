package org.example.authmicroservice.service;

import lombok.RequiredArgsConstructor;
import org.example.authmicroservice.dao.entity.UserEntity;
import org.example.authmicroservice.dao.repository.UserRepository;
import org.example.authmicroservice.dto.LoginDto;
import org.example.authmicroservice.dto.RegisterDto;
import org.example.authmicroservice.dto.TokenResponse;
import org.example.authmicroservice.jwt.JwtService;
import org.example.authmicroservice.mapper.UserMapper;
import org.example.authmicroservice.redis.RedisService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisTemplate<Object, Object> redisTemplate;
    private final RedisService redisService;
    private final JwtService jwtService;

    public void register(RegisterDto dto){
        UserEntity user = userMapper.registerDtoToEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);
    }

    public TokenResponse login(LoginDto dto){
        UserEntity user = userRepository.findByUserName(dto.getUserName())
                .orElseThrow(() -> new RuntimeException("User not found"));
       if(passwordEncoder.matches(dto.getPassword(),user.getPassword())){
           return new TokenResponse(jwtService.generateAccessToken(user),
                   jwtService.generateRefreshToken(dto.getUserName()));
       }
       throw new RuntimeException("Invalid userName or Password");
    }

    public TokenResponse getRefreshToken(String refreshToken){
        String userName = jwtService.extractUsername(refreshToken);
        if(userName==null||!redisService.getRefreshToken(userName).equals(refreshToken))
            throw new RuntimeException("Refresh token mismatch or expiry");
        UserEntity user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("User not found"));
        redisService.deleteRefreshToken(userName);
        return new TokenResponse(jwtService.generateAccessToken(user), jwtService.generateRefreshToken(userName));
    }
}
