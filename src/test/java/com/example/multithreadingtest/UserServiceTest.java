package com.example.multithreadingtest;

import com.example.multithreadingtest.repository.IUserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;

/**
 * UserServiceTest.
 *
 * @Description TODO
 * @Date 13/05/2022 22:59
 * @Created by Qinxiu Wang
 */
@SpringBootTest
public class UserServiceTest {

  @Autowired
  private UserService userService;

  /**
   * Worked
   */
  @Test
  void dumb_async_testing() throws ExecutionException, InterruptedException {
    long startTime = System.currentTimeMillis();
    List<Future<String>> futureList = new ArrayList<>();
    List<String> finalResult = new ArrayList<>();

    for (int x = 1; x <= 7; x++) {
      Future<String> temp = userService.method("" + 1);
      futureList.add(temp);
    }

    for (Future<String> future : futureList) {
      finalResult.add(future.get());
    }

    System.out.println(finalResult);
    System.out.println(
        "total time spent: " + (System.currentTimeMillis() - startTime) + " ms");
  }
}
