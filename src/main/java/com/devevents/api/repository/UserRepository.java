package com.devevents.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.devevents.api.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    UserDetails findByEmail(String email);
}