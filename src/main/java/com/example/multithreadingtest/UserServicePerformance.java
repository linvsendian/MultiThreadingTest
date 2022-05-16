package com.example.multithreadingtest;

import com.example.multithreadingtest.api.service.UserService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import java.util.concurrent.Future;

public class UserServicePerformance {
    private final UserService userService;

    public UserServicePerformance(UserService userService) {
        this.userService = userService;
    }

    @Async
    public Future<String> showPerformance(String id) {
        long startTime = System.nanoTime();
        userService.getUserByEmail("email");
        long spendTime = System.nanoTime() - startTime;
        return new AsyncResult<>(Thread.currentThread().getName() + ", value: " + id + ", time spent: " + spendTime);
    }
}
