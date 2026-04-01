package org.example.authmicroservice.dto;

import lombok.*;
import org.example.authmicroservice.util.enums.Role;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginDto {
    private String userName,password;
}
