package com.swagger.swagger.repository;

import com.swagger.swagger.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @Query("SELECT u from UserEntity u where u.username = ?1")
    UserEntity findByUsername(String username);
}
