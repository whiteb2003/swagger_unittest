package com.swagger.swagger.repository;

import com.swagger.swagger.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query("""
Select t from Token t inner join UserEntity u
on t.user.id = :userId and t.loggedOut= false
""")
    List<Token> findByUserId(@Param("userId") Long userId);

    Optional<Token> findByToken(String token);
}
