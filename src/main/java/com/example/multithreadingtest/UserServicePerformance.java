package com.example.multithreadingtest;

import com.example.multithreadingtest.api.service.UserService;
import org.springframework.scheduling.annotation.AsyncResult;

import java.util.concurrent.Future;

public class UserServicePerformance extends Thread {
    private final UserService userService;

    public UserServicePerformance(UserService userService) {
        this.userService = userService;
    }

    private Future<String> showPerformance() {
        long startTime = System.nanoTime();
        userService.getUserByEmail("email");
        long spendTime = System.nanoTime() - startTime;
        String result = "Class: " + userService.getClass().getName() + " " + Thread.currentThread().getName() + ", time spent:" + spendTime;
        System.out.println(result);
        return new AsyncResult<>(result);
    }

    @Override
    public void run() {
        showPerformance();
    }
}
