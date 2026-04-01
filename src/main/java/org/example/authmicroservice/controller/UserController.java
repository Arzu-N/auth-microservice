package org.example.authmicroservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.example.authmicroservice.dto.LoginDto;
import org.example.authmicroservice.dto.RegisterDto;
import org.example.authmicroservice.dto.TokenResponse;
import org.example.authmicroservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.util.repeat.RepeatSpec;

@RestController
@RequiredArgsConstructor
@RequestMapping("/refresh-token/api/v1/auth")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Void>register(@RequestBody RegisterDto dto){
        userService.register(dto);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/login")
    public ResponseEntity<TokenResponse>login(@RequestBody LoginDto dto){
        return ResponseEntity.ok(userService.login(dto));
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<TokenResponse>getRefreshToken(@RequestParam String refreshToken){
        return ResponseEntity.ok(userService.getRefreshToken(refreshToken));
    }

}
