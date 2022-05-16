package com.example.multithreadingtest.api.service;

import com.example.multithreadingtest.api.model.User;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UserService {
    CompletableFuture<List<User>> getUserByEmail(String email);
}
