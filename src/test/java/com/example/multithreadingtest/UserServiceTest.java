package com.example.multithreadingtest;

import com.example.multithreadingtest.jpaquery.service.JpaUserService;
import com.example.multithreadingtest.nativequery.service.NativeUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private JpaUserService jpaUserService;

    @Autowired
    private NativeUserService nativeUserService;

    /**
     * Worked
     */
    @Test
    void dumbAsyncTesting() {
        // JPA
        List<Thread> threadList = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        for (int x = 1; x <= 1; x++) {
            UserServicePerformance jpaUserServicePerformance = new UserServicePerformance(jpaUserService);
            threadList.add(new Thread(jpaUserServicePerformance));
        }
        threadList.forEach(Thread::start);
        threadList.forEach(x -> {
            try {
                x.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("SINGLE JPA total time spent: " + (System.currentTimeMillis() - startTime) + " ms");


        threadList = new ArrayList<>();
        startTime = System.currentTimeMillis();
        for (int x = 1; x <= 7; x++) {
            UserServicePerformance jpaUserServicePerformance = new UserServicePerformance(jpaUserService);
            threadList.add(new Thread(jpaUserServicePerformance));
        }
        threadList.forEach(Thread::start);
        threadList.forEach(x -> {
            try {
                x.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("MULTI JPA total time spent: " + (System.currentTimeMillis() - startTime) + " ms");

        // Native
        threadList = new ArrayList<>();
        startTime = System.currentTimeMillis();
        for (int x = 1; x <= 1; x++) {
            UserServicePerformance nativeUserServicePerformance = new UserServicePerformance(nativeUserService);
            threadList.add(new Thread(nativeUserServicePerformance));
        }
        threadList.forEach(Thread::start);
        threadList.forEach(x -> {
            try {
                x.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("SINGLE Native total time spent: " + (System.currentTimeMillis() - startTime) + " ms");


        threadList = new ArrayList<>();
        startTime = System.currentTimeMillis();
        for (int x = 1; x <= 7; x++) {
            UserServicePerformance jpaUserServicePerformance = new UserServicePerformance(nativeUserService);
            threadList.add(new Thread(jpaUserServicePerformance));
        }
        threadList.forEach(Thread::start);
        threadList.forEach(x -> {
            try {
                x.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("MULTI Native total time spent: " + (System.currentTimeMillis() - startTime) + " ms");
    }
}
