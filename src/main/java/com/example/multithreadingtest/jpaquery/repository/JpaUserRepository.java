package com.example.multithreadingtest.jpaquery.repository;

import com.example.multithreadingtest.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface JpaUserRepository extends JpaRepository<User, Long> {
    @Async
    CompletableFuture<List<User>> findByEmail(String email);
}


