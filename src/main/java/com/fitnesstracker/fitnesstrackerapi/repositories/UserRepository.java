package com.fitnesstracker.fitnesstrackerapi.repositories;

import com.fitnesstracker.fitnesstrackerapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
