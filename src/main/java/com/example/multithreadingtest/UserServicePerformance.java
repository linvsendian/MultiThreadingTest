package com.example.multithreadingtest;

import com.example.multithreadingtest.api.service.UserService;


public class UserServicePerformance extends Thread {
    private final UserService userService;

    public UserServicePerformance(UserService userService) {
        this.userService = userService;
    }

    private void showPerformance() {
        long startTime = System.currentTimeMillis();
        userService.getUserByEmail("email");
        long spendTime = System.currentTimeMillis() - startTime;
        String result = "Class: " + userService.getClass().getName() + " " + Thread.currentThread().getName() + ", time spent:" + spendTime;
        System.out.println(result);
    }

    @Override
    public void run() {
        showPerformance();
    }
}
