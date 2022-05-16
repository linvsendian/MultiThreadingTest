package com.example.multithreadingtest;

import com.example.multithreadingtest.jpaquery.service.JpaUserService;
import com.example.multithreadingtest.nativequery.service.NativeUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.concurrent.Future;

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
        long startTime = System.nanoTime();
        UserServicePerformance jpaUserServicePerformance = new UserServicePerformance(jpaUserService);
        for (int x = 1; x <= 7; x++) {
            Future<String> temp = jpaUserServicePerformance.showPerformance("" + 1);
        }

        System.out.println("JPA total time spent: " + (System.nanoTime() - startTime) + " ms");

        // Native
        startTime = System.nanoTime();
        UserServicePerformance nativeUserServicePerformance = new UserServicePerformance(nativeUserService);
        for (int x = 1; x <= 7; x++) {
            nativeUserServicePerformance.showPerformance("" + 1);
        }

        System.out.println("Native total time spent: " + (System.nanoTime() - startTime) + " ms");
    }
}
