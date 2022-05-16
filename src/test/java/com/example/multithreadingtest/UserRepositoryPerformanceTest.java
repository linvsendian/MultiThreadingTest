package com.example.multithreadingtest;

import com.example.multithreadingtest.api.model.User;
import com.example.multithreadingtest.jpaquery.repository.JpaUserRepository;
import com.example.multithreadingtest.jpaquery.service.JpaUserService;
import com.example.multithreadingtest.nativequery.repository.NativeUserRepository;
import com.example.multithreadingtest.nativequery.service.NativeUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

@SpringBootTest
class UserRepositoryPerformanceTest {

    @Autowired
    private JpaUserRepository jpaUserRepository;

    @Autowired
    private NativeUserRepository nativeUserRepository;

    @Autowired
    private JpaUserService jpaUserService;

    @Autowired
    private NativeUserService nativeUserService;

    private UserServicePerformance jpaUserServicePerformance;

    private UserServicePerformance nativeUserServicePerformance;

    @BeforeEach
    public void saveUsers() {
        jpaUserServicePerformance = new UserServicePerformance(jpaUserService);
        nativeUserServicePerformance = new UserServicePerformance(nativeUserService);
        int maxSize = 100000;
        IntStream.rangeClosed(1, maxSize).forEach(
                x -> {
                    User tempUser = User.builder().id(x).name("user_" + x).email("email").build();
                    jpaUserRepository.save(tempUser);
                    nativeUserRepository.save(tempUser);
                }
        );
    }


    @Test
    void testPerformance() {
        int total = 7;
        IntStream.rangeClosed(1, total).forEach(
                x -> {
                    Future<String> jpaPerformance = jpaUserServicePerformance.showPerformance();
                    try {
                        System.out.println("JPA performance: " + jpaPerformance.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
        );
        IntStream.rangeClosed(1, total).forEach(
                x -> {
                    Future<String> nativePerformance = nativeUserServicePerformance.showPerformance();
                    try {
                        System.out.println("Native performance" + nativePerformance.get());
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
        );
    }
}
