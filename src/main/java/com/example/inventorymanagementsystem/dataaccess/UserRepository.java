package com.example.inventorymanagementsystem.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.inventorymanagementsystem.model.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByUsername(String username);

}
