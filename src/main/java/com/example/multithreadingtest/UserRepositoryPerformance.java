package com.example.multithreadingtest;


import com.example.multithreadingtest.api.model.User;
import com.example.multithreadingtest.jpaquery.repository.JpaUserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class UserRepositoryPerformance {

    private final JpaUserRepository jpaRepository;

    public UserRepositoryPerformance(JpaUserRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    public void showPerformance(int size) {

        List<CompletableFuture<List<User>>> threadList = new ArrayList<>();
        for (int x = 0; x < size; x++) {
            threadList.add(jpaRepository.findByEmailAsync("email"));
        }
        threadList.forEach(CompletableFuture::join);
    }
}
