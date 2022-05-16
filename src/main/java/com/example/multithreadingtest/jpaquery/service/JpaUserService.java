package com.example.multithreadingtest.jpaquery.service;

import com.example.multithreadingtest.jpaquery.repository.JpaUserRepository;
import com.example.multithreadingtest.api.model.User;
import com.example.multithreadingtest.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class JpaUserService implements UserService {

    @Autowired
    private JpaUserRepository userRepository;

    @Override
    public CompletableFuture<List<User>> getUserByEmail(String email) {
        return userRepository.findByEmail("email");
    }
}
