package com.example.multithreadingtest;

import com.example.multithreadingtest.model.User;
import com.example.multithreadingtest.repository.IUserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    int maxSize = 100000;
    IntStream.rangeClosed(1, maxSize).forEach(
        x -> {
          User tempUser = User.builder().id(x).name("user_" + x).email("email").build();
          userRepository.save(tempUser);
        }
    );
  }


  @Test
  void sequence_query_test() {
    // Arrange
    long currentTimeMillis = System.currentTimeMillis();
    int range = 3;
    List<User> responseList = new ArrayList<>();

    // Act
    IntStream.rangeClosed(1, range).forEach(
        x -> {
          long startTime = System.currentTimeMillis();
          responseList.addAll(userRepository.findByEmail("email"));
          System.out.println(
              Thread.currentThread().getName()
                  + ", Index: " + x
                  + "  ,spent time: " + (System.currentTimeMillis() - startTime) + " ms");
        }
    );

    System.out.println("response size: " + responseList.size());
    System.out.println(
        "total spent time: " + (System.currentTimeMillis() - currentTimeMillis) + " ms");
  }

  @Test
  void non_native_query_async_test()
      throws ExecutionException, InterruptedException {
    // Arrange
    long currentTimeMillis = System.currentTimeMillis();
    int parallelism = 3;

    // Act
    List<CompletableFuture<List<User>>> futureList = IntStream.rangeClosed(1, parallelism)
        .mapToObj(x -> userRepository.findAllByEmail("email"))
        .collect(Collectors.toList());

    //list to array conversion
    CompletableFuture<Void> resultantCf = CompletableFuture.allOf(
        futureList.toArray(new CompletableFuture[0]));

    //join all async processes
    CompletableFuture<List<List<User>>> allFutureResults = resultantCf
        .thenApply(t -> futureList.stream().map(CompletableFuture::join).collect(
            Collectors.toList()));

    System.out.println("Result - " + allFutureResults.get().size());
    System.out.println(
        "total spent time: " + (System.currentTimeMillis() - currentTimeMillis) + " ms");
  }

  @Test
  void native_query_async_test()
      throws ExecutionException, InterruptedException {
    // Arrange
    long currentTimeMillis = System.currentTimeMillis();
    int parallelism = 7;

    // Act
    List<CompletableFuture<List<User>>> futureList = IntStream.rangeClosed(1, parallelism)
        .mapToObj(x -> userRepository.findByEmailNativeAsync("email"))
        .collect(Collectors.toList());

    //list to array conversion
    CompletableFuture<Void> resultantCf = CompletableFuture.allOf(
        futureList.toArray(new CompletableFuture[0]));

    //join all async processes
    CompletableFuture<List<List<User>>> allFutureResults = resultantCf
        .thenApply(t -> futureList.stream().map(CompletableFuture::join).collect(
            Collectors.toList()));

    System.out.println("Result - " + allFutureResults.get().size());
    System.out.println(
        "total spent time: " + (System.currentTimeMillis() - currentTimeMillis) + " ms");
  }
}
