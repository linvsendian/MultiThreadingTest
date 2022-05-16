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

import java.util.ArrayList;
import java.util.List;
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
        List<Thread> threadList = new ArrayList<>();
        IntStream.rangeClosed(1, total).forEach(
                x -> {
                    threadList.add(new Thread(new UserServicePerformance(jpaUserService)));
                }
        );
        threadList.forEach(Thread::start);
        threadList.forEach(x -> {
            try {
                x.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        List<Thread> threadList2 = new ArrayList<>();
        IntStream.rangeClosed(1, total).forEach(
                x -> {
                    threadList2.add(new Thread(new UserServicePerformance(nativeUserService)));
                }
        );
        threadList2.forEach(Thread::start);
        threadList2.forEach(x -> {
            try {
                x.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
