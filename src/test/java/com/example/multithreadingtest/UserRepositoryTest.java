package com.example.multithreadingtest;

import com.example.multithreadingtest.model.IUserProjection;
import com.example.multithreadingtest.model.User;
import com.example.multithreadingtest.repository.IUserRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import java.util.stream.Collectors;
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
  void no_native_query_async_test() throws ExecutionException, InterruptedException {
    // Arrange
    long currentTimeMillis = System.currentTimeMillis();
    int parallelism = 7;
    List<Future<List<User>>> futureList = new ArrayList<>();
    List<User> responseList = new ArrayList<>();

    // Act
    IntStream.rangeClosed(1, parallelism).forEach(
        x -> futureList.add(userRepository.findByEmail("email"))
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

//  @Test
//  void native_query_based_interface_projection_multiThreading_test()
//      throws ExecutionException, InterruptedException {
//    // Arrange
//    long currentTimeMillis = System.currentTimeMillis();
//    int parallelism = 7;
//    List<Future<List<IUserProjection>>> futureList = new ArrayList<>();
//    List<IUserProjection> responseList = new ArrayList<>();
//
//    // Act
//    IntStream.rangeClosed(1, parallelism).forEach(
//        x -> futureList.add(userRepository.findByEmailNativeAsync("email"))
//    );
//
//    for (Future<List<IUserProjection>> future : futureList) {
//      List<IUserProjection> tempResponse = future.get();
//      responseList.addAll(tempResponse);
//    }
//
//    // Assert
//    System.out.println("response size: " + responseList.size());
//    System.out.println(
//        "total spent time: " + (System.currentTimeMillis() - currentTimeMillis) + " ms");
//  }


  @Test
  void asyncTest() throws ExecutionException, InterruptedException {

    long currentTimeMillis = System.currentTimeMillis();
    int parallelism = 8;

    List<User> result = new ArrayList<>();
    for (int i = 0; i < parallelism; i++) {
      userRepository.findByEmailNative("email").join();
    }

    System.out.println("response size: " + result.size());
    System.out.println(
        "total spent time: " + (System.currentTimeMillis() - currentTimeMillis) + " ms");
  }

  @Test
  void asyncTest_no_native() throws ExecutionException, InterruptedException {
    //Arrange
    long currentTimeMillis = System.currentTimeMillis();
    int parallelism = 5;

    //Act
    List<CompletableFuture<List<User>>> futures = new ArrayList<>();
    List<User> result = new ArrayList<>();

    for (int i = 0; i < parallelism; i++) {
//      futures.add(userRepository.findByEmail("email"));
    }

    for (int i = 0; i < parallelism; i++) {
      result.addAll(futures.get(i).get());
    }

    System.out.println("response size: " + result.size());
    System.out.println(
        "total spent time: " + (System.currentTimeMillis() - currentTimeMillis) + " ms");
  }


  @Test
  void async_retun_value_testing() throws ExecutionException, InterruptedException {
    Executor executor = Executors.newFixedThreadPool(10);
    CompletableFuture<String> future = CompletableFuture.supplyAsync(new Supplier<String>() {
      @Override
      public String get() {
        try {
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
          throw new IllegalStateException(e);
        }
        return  Thread.currentThread().getName() +  " --> Result of the asynchronous computation";
      }
    },executor);



// Block and get the result of the Future
    String result = future.get();
    System.out.println(result);
  }

  @Test
  void async_test() throws ExecutionException, InterruptedException {

    CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> {
      try {
        TimeUnit.SECONDS.sleep(2);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      String stringToPrint = "Educative";
      System.out.println("----\nsupplyAsync first future - " + stringToPrint);
      System.out.println("Thread execution - " + Thread.currentThread().getName());
      return stringToPrint;
    });

    CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> {
      try {
        TimeUnit.SECONDS.sleep(2);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      String stringToPrint = "Edpresso";
      System.out.println("----\nsupplyAsync second future - " + stringToPrint);
      System.out.println("Thread execution - " + Thread.currentThread().getName());
      return stringToPrint;
    });

    List<CompletableFuture<String>> completableFutures = Arrays.asList(completableFuture1, completableFuture2);

    CompletableFuture<Void> resultantCf = CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[completableFutures.size()]));

    CompletableFuture<List<String>> allFutureResults = resultantCf.thenApply(t -> completableFutures.stream().map(CompletableFuture::join).collect(
        Collectors.toList()));

    System.out.println("Result - " + allFutureResults.get());
  }




}
