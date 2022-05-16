package com.example.multithreadingtest.nativequery.service;

import com.example.multithreadingtest.api.model.User;
import com.example.multithreadingtest.api.service.UserService;
import com.example.multithreadingtest.nativequery.repository.NativeUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class NativeUserService implements UserService {
    @Autowired
    private NativeUserRepository userRepository;

    @Override
    public CompletableFuture<List<User>> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
