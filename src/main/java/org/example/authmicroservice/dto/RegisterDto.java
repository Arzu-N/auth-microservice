package org.example.authmicroservice.dto;

import lombok.*;
import org.example.authmicroservice.util.enums.Role;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RegisterDto {
    private String userName,password,email;
    private Role role;
}
