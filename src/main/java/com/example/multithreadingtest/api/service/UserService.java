package com.example.multithreadingtest.api.service;

import com.example.multithreadingtest.api.model.User;

import java.util.List;

public interface UserService {
    List<User> getUserByEmail(String email);
}
