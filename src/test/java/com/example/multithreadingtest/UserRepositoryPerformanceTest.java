package com.example.multithreadingtest;

import com.example.multithreadingtest.api.model.User;
import com.example.multithreadingtest.jpaquery.repository.JpaUserRepository;
import com.example.multithreadingtest.nativequery.repository.NativeUserRepository;
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
    void testMultPerformance() {
        System.out.println("START 1");
        new UserRepositoryPerformance(jpaUserRepository).showPerformance(1);
        System.out.println("END 1");
        System.out.println("=============================================");
        System.out.println("START 2");
        new UserRepositoryPerformance(jpaUserRepository).showPerformance(2);
        System.out.println("END 2");
        System.out.println("=============================================");
        System.out.println("START 3");
        new UserRepositoryPerformance(jpaUserRepository).showPerformance(3);
        System.out.println("END 3");
        System.out.println("=============================================");
        System.out.println("START 4");
        new UserRepositoryPerformance(jpaUserRepository).showPerformance(4);
        System.out.println("END 4");
        System.out.println("=============================================");
        System.out.println("START 5");
        new UserRepositoryPerformance(jpaUserRepository).showPerformance(5);
        System.out.println("END 5");
        System.out.println("=============================================");
        System.out.println("START 6");
        new UserRepositoryPerformance(jpaUserRepository).showPerformance(6);
        System.out.println("END 6");
    }
}
