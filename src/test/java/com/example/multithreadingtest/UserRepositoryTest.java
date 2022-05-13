package com.example.multithreadingtest;

import com.example.multithreadingtest.model.IUserProjection;
import com.example.multithreadingtest.model.User;
import com.example.multithreadingtest.repository.IUserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

/**
 * userRepositoryTest.
 *
 * @Description TODO
 * @Date 11/01/2022 20:54
 * @Created by Qinxiu Wang
 */
@SpringBootTest
public class UserRepositoryTest {

  @Autowired
  private IUserRepository userRepository;


  @BeforeEach
  void save_users() {
    int maxSize = 40000;
    IntStream.rangeClosed(1, maxSize).forEach(
        x -> {
          User tempUser = User.builder().id(x).name("user_" + x).email("email").build();
          userRepository.save(tempUser);
        }
    );
  }

  @Test
  void no_native_query_async_test()
      throws ExecutionException, InterruptedException {
    // Arrange
    long currentTimeMillis = System.currentTimeMillis();
    int parallelism = 7;
    List<Future<List<User>>> futureList = new ArrayList<>();
    List<User> responseList = new ArrayList<>();

    // Act
    IntStream.rangeClosed(1, parallelism).forEach(
        x -> futureList.add(userRepository.findByEmailAsync("email"))
    );

    for (Future<List<User>> future : futureList) {
      List<User> tempResponse = future.get();
      responseList.addAll(tempResponse);
    }

    // Assert
    System.out.println("response size: " + responseList.size());
    System.out.println(
        "total spent time: " + (System.currentTimeMillis() - currentTimeMillis) + " ms");
  }


  @Test
  void native_query_async_test() throws ExecutionException, InterruptedException {

    long currentTimeMillis = System.currentTimeMillis();
    int parallelism = 7;

    List<User> result = new ArrayList<>();
    for (int i = 0; i < parallelism; i++) {
      result.addAll(userRepository.findByEmailNativeAsync("email").get());
    }

    System.out.println("response size: " + result.size());
    System.out.println(
        "total spent time: " + (System.currentTimeMillis() - currentTimeMillis) + " ms");
  }


}
