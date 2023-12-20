package com.oauth.clientserver.repository;

import com.oauth.clientserver.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findByEmail(String email);





}
