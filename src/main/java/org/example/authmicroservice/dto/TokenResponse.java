package org.example.authmicroservice.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TokenResponse {
    private String accessToken,refreshToken;
}
