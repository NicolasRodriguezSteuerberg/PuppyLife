package com.puppylife.backend.security.persistance.repository;

import com.puppylife.backend.security.persistance.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findUserByUsernameOrEmail(String username, String email);
}
