package com.example.multithreadingtest;

import com.example.multithreadingtest.repository.IUserRepository;
import java.util.concurrent.Future;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

/**
 * AsyncService.
 *
 * @Description TODO
 * @Date 14/12/2021 12:21
 * @Created by Qinxiu Wang
 */
@Service
public class AsyncService {
  @Autowired
  private IUserRepository userRepository;

  @Async
  public Future<String> getMessageAsync(int num, int time) throws InterruptedException {
    Thread.sleep(time);
    return new AsyncResult<>("hello : " + num + " - " + Thread.currentThread().getName());
  }


  @Async
  public Future<String> method1(String str) {
    long startTime = System.currentTimeMillis();
    userRepository.findByEmail("email");
    long spendTime = System.currentTimeMillis() - startTime;
    return new AsyncResult<>(
        Thread.currentThread().getName() + ", value: " + str + ", time spent: " + spendTime);
  }

  @Async
  public Future<String> method2(String str) {
    long startTime = System.currentTimeMillis();
    userRepository.findByEmail("email");
    long spendTime = System.currentTimeMillis() - startTime;
    return new AsyncResult<>(
        Thread.currentThread().getName() + ", value: " + str + ", time spent: " + spendTime);
  }

  @Async
  public Future<String> method3(String str) {
    long startTime = System.currentTimeMillis();
    userRepository.findByEmail("email");
    long spendTime = System.currentTimeMillis() - startTime;
    return new AsyncResult<>(
        Thread.currentThread().getName() + ", value: " + str + ", time spent: " + spendTime);
  }
}
