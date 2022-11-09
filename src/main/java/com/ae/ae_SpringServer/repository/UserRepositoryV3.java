package com.ae.ae_SpringServer.repository;

import com.ae.ae_SpringServer.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryV3 extends JpaRepository<User, Long> {
    Optional<User> findById(Long userId);
}