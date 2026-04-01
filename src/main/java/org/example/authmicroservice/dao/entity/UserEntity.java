package org.example.authmicroservice.dao.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.authmicroservice.util.enums.Role;

@Entity
@AllArgsConstructor
@NoArgsConstructor@Setter@Getter
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName,email,password;
@Enumerated(EnumType.STRING)
    private Role role;
}
