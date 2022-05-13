package com.example.multithreadingtest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MultithreadingTestApplicationTests {

  @Autowired
  AsyncService asyncService;

  @Test
  void asyncTest() throws InterruptedException, ExecutionException {
    long currentTimeMillis = System.currentTimeMillis();
    Future<String> future = asyncService.getMessageAsync(7, 20);

    String response = future.get();
    System.out.println("spend time: " + (System.currentTimeMillis() - currentTimeMillis));
    System.out.println("--> " + response);
  }

  @Test
  void asyncListTest() throws InterruptedException, ExecutionException {
    long currentTimeMillis = System.currentTimeMillis();
    List<Future<String>> futureList = new ArrayList<>();
    List<String> responseList = new ArrayList<>();

    for (int x = 1; x <= 5; x++) {
      futureList.add(asyncService.getMessageAsync(x, 2000));
    }

    for (Future<String> future : futureList) {
      responseList.add(future.get());
    }

    System.out.println("spend time: " + (System.currentTimeMillis() - currentTimeMillis));
    responseList.forEach(System.out::println);
  }

  @Test
  void serie_async_testing() throws ExecutionException, InterruptedException {
    CompletableFuture<String> finalResult =  CompletableFuture.supplyAsync(() -> {
      return "Some Result";
    }).thenApplyAsync(result -> {
      // Executed in a different thread from ForkJoinPool.commonPool()
      return "Processed Result";
    });

    System.out.println(finalResult.get());
  }

}
