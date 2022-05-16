package com.example.multithreadingtest;

import com.example.multithreadingtest.api.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class UserServicePerformance {
    private final UserService userService;

    public UserServicePerformance(UserService userService) {
        this.userService = userService;
    }


    public void showPerformance(int size) throws InterruptedException {
        List<Thread> threadList = new ArrayList<>();
        for (int x = 0; x < size; x++) {
            Thread t = new Thread(() -> userService.getUserByEmail("email"));
            threadList.add(t);
        }
        threadList.forEach(Thread::start);
        Thread.sleep(4000);
    }
}
