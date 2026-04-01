package org.example.authmicroservice.dao.repository;

import org.example.authmicroservice.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity>findByUserName(String userName);
}
