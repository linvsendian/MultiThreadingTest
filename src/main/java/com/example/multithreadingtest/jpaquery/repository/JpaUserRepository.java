package com.example.multithreadingtest.jpaquery.repository;

import com.example.multithreadingtest.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface JpaUserRepository extends JpaRepository<User, Long> {
    @Async
    CompletableFuture<List<User>> findByEmail(String email);

    @Query(value = ""
            + "SELECT utb.id, utb.name, utb.email "
            + "FROM user_tb utb WHERE email = :email ", nativeQuery = true)
    List<User> myFindUser(String email);

    @Async
    default CompletableFuture<List<User>> findByEmailAsync(String email) {
        return CompletableFuture.supplyAsync(() -> {
            long startTime = System.currentTimeMillis();
            System.out.println("Name:" + Thread.currentThread().getName() + " + StartTime:" + startTime);
            List<User> users = myFindUser(email);
            System.out.println("Name: " + Thread.currentThread().getName() + " NATIVE Spent time: " + (System.currentTimeMillis() - startTime));
            return users;
        });
    }
}


