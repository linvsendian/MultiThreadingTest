package com.example.multithreadingtest;

import com.example.multithreadingtest.model.User;
import com.example.multithreadingtest.service.GitHubLookupService;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;

@SpringBootTest
@Slf4j
class MultithreadingTestApplicationTests {

  @Autowired
  private GitHubLookupService gitHubLookupService;

  @Test
  void github_async_sample_only_one_process() throws InterruptedException, ExecutionException {
    // Start the clock
    long start = System.currentTimeMillis();

    // Kick of multiple, asynchronous lookups
    CompletableFuture<User> page1 = gitHubLookupService.findUser("PivotalSoftware");

    // Wait until they are all done
    CompletableFuture.allOf(page1).join();

    // Print results, including elapsed time
    log.info("Elapsed time: " + (System.currentTimeMillis() - start));
    log.info("--> " + page1.get());
  }


  @Test
  void github_async_sample() throws InterruptedException, ExecutionException {
    // Start the clock
    long start = System.currentTimeMillis();

    // Kick of multiple, asynchronous lookups
    CompletableFuture<User> page1 = gitHubLookupService.findUser("PivotalSoftware");
    CompletableFuture<User> page2 = gitHubLookupService.findUser("CloudFoundry");
    CompletableFuture<User> page3 = gitHubLookupService.findUser("Spring-Projects");

    // Wait until they are all done
    CompletableFuture.allOf(page1, page2, page3).join();

    // Print results, including elapsed time
    log.info("Elapsed time: " + (System.currentTimeMillis() - start));
    log.info("--> " + page1.get());
    log.info("--> " + page2.get());
    log.info("--> " + page3.get());
  }


  @Test
  void github_async_sample_same_process() throws InterruptedException, ExecutionException {
    // Start the clock
    long start = System.currentTimeMillis();

    // Kick of multiple, asynchronous lookups
    CompletableFuture<User> page1 = gitHubLookupService.findUser("PivotalSoftware");

    // Wait until they are all done
    CompletableFuture.allOf(page1, page1, page1).join();

    // Print results, including elapsed time
    log.info("Elapsed time: " + (System.currentTimeMillis() - start));
    log.info("--> " + page1.get());
    log.info("--> " + page1.get());
    log.info("--> " + page1.get());
  }

  @Test
  void github_async_sample_same_process_and_loop() throws InterruptedException, ExecutionException {
    // Start the clock
    long currentTimeMillis = System.currentTimeMillis();
    int parallelism = 7;

    // Kick of multiple, asynchronous lookups
    List<CompletableFuture<User>> futureList = IntStream.rangeClosed(1, parallelism)
        .mapToObj(x -> {
          try {
            return gitHubLookupService.findUser("PivotalSoftware");
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          return null;
        })
        .collect(Collectors.toList());

    //list to array conversion
    CompletableFuture<Void> resultantCf = CompletableFuture.allOf(
        futureList.toArray(new CompletableFuture[0]));

    //join all async processes
    CompletableFuture<List<User>> allFutureResults = resultantCf
        .thenApply(t -> futureList.stream().map(CompletableFuture::join).collect(
            Collectors.toList()));

    // Print results, including elapsed time
    log.info("--> " + allFutureResults.get());
    log.info(
        "total spend time: " + (System.currentTimeMillis() - currentTimeMillis) + " milliseconds");
  }


  /* 25126 ms*/
  @Test
  void async_post_query_test() throws URISyntaxException, ExecutionException, InterruptedException {
    // Start the clock
    long start = System.currentTimeMillis();

    // Kick of multiple, asynchronous lookups
    CompletableFuture<String> page1 = gitHubLookupService.postRequest(1);
    CompletableFuture<String> page2 = gitHubLookupService.postRequest(2);
    CompletableFuture<String> page3 = gitHubLookupService.postRequest(3);

    // Wait until they are all done
    CompletableFuture.allOf(page1, page2, page3).join();

    // Print results, including elapsed time
    log.info("Elapsed time: " + (System.currentTimeMillis() - start));
    log.info("--> " + page1.get());
    log.info("--> " + page2.get());
    log.info("--> " + page3.get());
  }


  /*22666 ms*/
  @Test
  void async_post_query_test_only_one()
      throws URISyntaxException, ExecutionException, InterruptedException {
    // Start the clock
    long start = System.currentTimeMillis();

    // Kick of multiple, asynchronous lookups
    CompletableFuture<String> page1 = gitHubLookupService.postRequest(1);

    // Wait until they are all done
    CompletableFuture.allOf(page1).join();

    // Print results, including elapsed time
    log.info("Elapsed time: " + (System.currentTimeMillis() - start));
    log.info("--> " + page1.get());
  }


  @Test
  void async_post_query_same_process_and_loop() throws InterruptedException, ExecutionException {
    // Start the clock
    long currentTimeMillis = System.currentTimeMillis();
    int parallelism = 7;

    // Kick of multiple, asynchronous lookups
    List<CompletableFuture<String>> futureList = IntStream.rangeClosed(1, parallelism)
        .mapToObj(x -> {
          try {
            return gitHubLookupService.postRequest(x);
          } catch (URISyntaxException e) {
            e.printStackTrace();
          }
          return null;
        })
        .collect(Collectors.toList());

    //list to array conversion
    CompletableFuture<Void> resultantCf = CompletableFuture.allOf(
        futureList.toArray(new CompletableFuture[0]));

    //join all async processes
    CompletableFuture<List<String>> allFutureResults = resultantCf
        .thenApply(t -> futureList.stream().map(CompletableFuture::join).collect(
            Collectors.toList()));

    // Print results, including elapsed time
    log.info("--> " + allFutureResults.get());
    log.info(
        "total spend time: " + (System.currentTimeMillis() - currentTimeMillis) + " milliseconds");
  }


  @Test
  void async_post_query_ASPM_process_and_loop() throws InterruptedException, ExecutionException {
    List<String> aspmList = Arrays.asList("AB - CARLOS ARAU", "AA - ALEX ARESPACOCHAGA",
        "AC - LAURA REDONDO", "AD - MIRIAM BARRERA", "BA - JORGE GARCÍA SANTAMARÍA",
        "BB - CESAR CHAVARRY", "BC - BRUNO LERIAS", "BD - RICARD GEY", "CA - VICTOR MANGAS",
        "CB - DAVID MARÍN", "CC - ANA REDONDO", "CD - GERARD BASART");
    String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRhIjoiQVFDUFliRWJsSVFqVFNsRWl1SHAxbWdaeDhIUHFlb2lVR0dPcUIwMm9vemJESE9SRkFweTg1c2hNMyt6bVgzcmo2VjBTY1pmdkt1aW1EWGc5ZXV5UERTaEtmSkJ5clllL3FhU1JrWlJ6RXlydFJ1RjRUSVptNWtmY3ZMMmtjTTdJa3pHellkdWl6eWpmcW5HWTY4Z3ZWTTRodmRNU0xpVXRHWktIUDc5L1VYeHN0UUZsWlZIRDB1Y2dTZU44cHZzV01yWlA3MkxoTExWZnVnVHlTTndxWTJwUmJDakMzRUppK3ZNU3BNckZzZWpMMVVoQmgwZ09iS3pjbmhxcEhvamE5SDgvczlOZFdVMUZRdTZsYzRrV3VNYlJVZ0I2S2kwditXcW5wbnJubllKYmVEMlplMFU4TmcwSm9xMUpYQk12c2ZDR1ZsVUtsMDZEdWVBMDhnTGRscUpHZk9HUUJ4T2wwQy95UjhQVlZCd1NsSTdGKzM4M3M4VHlXcG5kN1U1VmRpVFNBY0x6VytKVjc3ZVM0bkVVenRFbGRnaklEVHdVS1hObVFYNFZrcnhqQkQvdllxSTMwT3F5Vlo2VzhCajgzND0iLCJleHAiOjE2NTI0OTE0MTAsImlhdCI6MTY1MjQ4NDIxMH0.2QjDtdWMS0e-kx0ciI5ry3_L5amlM2zN7Adiug6j9fI";

    // Start the clock
    long currentTimeMillis = System.currentTimeMillis();
    int parallelism = 7;

    // Kick of multiple, asynchronous lookups
    List<CompletableFuture<String>> futureList = aspmList.stream()
        .map(x -> {
          try {
            return gitHubLookupService.postRequest(x, token);
          } catch (URISyntaxException e) {
            e.printStackTrace();
          }
          return null;
        })
        .collect(Collectors.toList());

    //list to array conversion
    CompletableFuture<Void> resultantCf = CompletableFuture.allOf(
        futureList.toArray(new CompletableFuture[0]));

    //join all async processes
    CompletableFuture<List<String>> allFutureResults = resultantCf
        .thenApply(t -> futureList.stream().map(CompletableFuture::join).collect(
            Collectors.toList()));

    // Print results, including elapsed time
    log.info("--> " + allFutureResults.get());
    log.info(
        "total spend time: " + (System.currentTimeMillis() - currentTimeMillis) + " milliseconds");
  }
}
