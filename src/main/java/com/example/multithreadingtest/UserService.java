package com.example.multithreadingtest;

import com.example.multithreadingtest.repository.IUserRepository;
import java.util.concurrent.Future;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

/**
 * userService.
 *
 * @Description TODO
 * @Date 13/05/2022 22:58
 * @Created by Qinxiu Wang
 */
@Service
public class UserService {

  @Autowired
  private IUserRepository userRepository;

  @Async
  public Future<String> method(String str) {
    long startTime = System.currentTimeMillis();
    userRepository.findByEmail("email");
    long spendTime = System.currentTimeMillis() - startTime;
    return new AsyncResult<>(
        Thread.currentThread().getName() + ", value: " + str + ", time spent: " + spendTime);
  }
}
