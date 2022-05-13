package com.example.multithreadingtest;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;

@SpringBootTest
class MultithreadingTestApplicationTests {


  @Async
  public CompletableFuture<String> asyncProcess(int index) {
    return CompletableFuture.supplyAsync(() -> {
      long startTime = System.currentTimeMillis();
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      String stringToPrint = "process-" + index;
      System.out.println(
          "Thread execution - " + Thread.currentThread().getName() + " spend time: " + (
              System.currentTimeMillis() - startTime) + " milliseconds");
      return stringToPrint;
    });
  }

  @Test
  void async_test_sample() throws ExecutionException, InterruptedException {
    long currentTimeMillis = System.currentTimeMillis();
    int parallelism = 7;

    //set futureList
    List<CompletableFuture<String>> futureList = IntStream.rangeClosed(1, parallelism)
        .mapToObj(this::asyncProcess)
        .collect(Collectors.toList());

    //list to array conversion
    CompletableFuture<Void> resultantCf = CompletableFuture.allOf(
        futureList.toArray(new CompletableFuture[0]));

    //join all async processes
    CompletableFuture<List<String>> allFutureResults = resultantCf
        .thenApply(t -> futureList.stream().map(CompletableFuture::join).collect(
            Collectors.toList()));

    System.out.println("Result - " + allFutureResults.get());
    System.out.println(
        "total spend time: " + (System.currentTimeMillis() - currentTimeMillis) + " milliseconds");
  }
}
