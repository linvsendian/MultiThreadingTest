package com.example.multithreadingtest.jpaquery.repository;

import com.example.multithreadingtest.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaUserRepository extends JpaRepository<User, Long> {

    List<User> findByEmail(String email);
}


