package com.example.multithreadingtest.nativequery.repository;

import com.example.multithreadingtest.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;


public interface NativeUserRepository extends JpaRepository<User, Long> {

    @Query(value = ""
            + "SELECT utb.id, utb.name, utb.email "
            + "FROM user_tb utb WHERE email = :email ", nativeQuery = true)
    CompletableFuture<List<User>> findByEmail(String email);

}


