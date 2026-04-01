package org.example.authmicroservice.redis;

import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;
private  static final String ACCESS_TOKEN_PREFIX="access-token:";
private static final String REFRESH_TOKEN_PREFIX="refresh-token:";


    public void saveAccessToken(String userName,String accessToken){
        redisTemplate.opsForValue().set(ACCESS_TOKEN_PREFIX+userName,accessToken,1,TimeUnit.MINUTES);
    }

    public void saveRefreshToken(String userName,String refreshToken){
        redisTemplate.opsForValue().set(REFRESH_TOKEN_PREFIX+userName,refreshToken,2, TimeUnit.MINUTES);
    }

    public String getRefreshToken(String userName){
        return (String) redisTemplate.opsForValue().get(REFRESH_TOKEN_PREFIX+userName);
    }

    public void deleteRefreshToken(String userName){
        redisTemplate.delete(REFRESH_TOKEN_PREFIX+userName);

    }
}
