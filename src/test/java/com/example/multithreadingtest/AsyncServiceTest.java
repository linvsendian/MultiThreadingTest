package com.example.multithreadingtest;

import com.example.multithreadingtest.service.AsyncService;
import com.example.multithreadingtest.service.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;

/**
 * AsyncServiceTest.
 *
 * @Description TODO
 * @Date 13/05/2022 22:17
 * @Created by Qinxiu Wang
 */
@SpringBootTest
public class AsyncServiceTest {

  @Autowired
  private AsyncService asyncService;

  @Autowired
  private UserService userService;

  @BeforeEach
  void save_users() {
    userService.saveUsers();
  }

  @Test
  void async_test() throws InterruptedException, ExecutionException {
    long startTime = System.currentTimeMillis();

    Future<String> result1 = asyncService.method1("I");
    Future<String> result2 = asyncService.method2("love");
    Future<String> result3 = asyncService.method3("async");

    String str = result1.get();
    String str2 = result2.get();
    String str3 = result3.get();

    String result = str + "\n" + str2 + "\n" + str3;

    System.out.println(result);
    System.out.println(
        "total time spent: " + (System.currentTimeMillis() - startTime) + " ms");
  }


  /**
   * false.
   */
  @Test
  void async_loop_testing() throws InterruptedException, ExecutionException {
    long startTime = System.currentTimeMillis();
    List<Future<String>> futureList = new ArrayList<>();
    List<String> finalResult = new ArrayList<>();

    for (int x = 1; x <= 1; x++) {
      Future<String> temp = asyncService.method1("" + x);
      futureList.add(temp);
    }

    for (Future<String> future : futureList) {
      finalResult.add(future.get());
    }

    finalResult.forEach(System.out::println);
    System.out.println(
        "total time spent: " + (System.currentTimeMillis() - startTime) + " ms");
  }


}
