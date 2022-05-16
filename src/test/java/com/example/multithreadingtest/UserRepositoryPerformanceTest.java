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


    @BeforeEach
    public void saveUsers() {
        int maxSize = 300000;
        IntStream.rangeClosed(1, maxSize).forEach(
                x -> {
                    User tempUser = User.builder().id(x).name("user_" + x).email("email").build();
                    jpaUserRepository.save(tempUser);
                    nativeUserRepository.save(tempUser);
                }
        );
    }

    @Test
    void testMultPerformance() throws InterruptedException {
        System.out.println("START 1");
        new UserServicePerformance(jpaUserService).showPerformance(1);
        new UserServicePerformance(nativeUserService).showPerformance(1);
        System.out.println("END 1");
        System.out.println("=============================================");
        System.out.println("START 2");
        new UserServicePerformance(jpaUserService).showPerformance(2);
        new UserServicePerformance(nativeUserService).showPerformance(2);
        System.out.println("END 2");
        System.out.println("=============================================");
        System.out.println("START 3");
        new UserServicePerformance(jpaUserService).showPerformance(3);
        new UserServicePerformance(nativeUserService).showPerformance(3);
        System.out.println("END 3");
        System.out.println("=============================================");
        System.out.println("START 4");
        new UserServicePerformance(jpaUserService).showPerformance(4);
        new UserServicePerformance(nativeUserService).showPerformance(4);
        System.out.println("END 4");
        System.out.println("=============================================");
        System.out.println("START 5");
        new UserServicePerformance(jpaUserService).showPerformance(5);
        new UserServicePerformance(nativeUserService).showPerformance(5);
        System.out.println("END 5");
        System.out.println("=============================================");
        System.out.println("START 6");
        new UserServicePerformance(jpaUserService).showPerformance(6);
        new UserServicePerformance(nativeUserService).showPerformance(6);
        System.out.println("END 6");
    }
}
