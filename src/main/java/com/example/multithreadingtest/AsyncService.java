package com.example.multithreadingtest;

import java.util.concurrent.Future;
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

  @Async
  public Future<String> getMessageAsync(int num, int time) throws InterruptedException {
    Thread.sleep(time);
    return new AsyncResult<>("hello : " + num + " - " + Thread.currentThread().getName());
  }


  @Async
  public Future<String> method1(String str) throws InterruptedException {
    long startTime = System.currentTimeMillis();
    Thread.sleep(1000 * 10);
    long spendTime = System.currentTimeMillis() - startTime;
    return new AsyncResult<>(
        Thread.currentThread().getName() + ", value: " + str + ", time spent: " + spendTime);
  }

  @Async
  public Future<String> method2(String str) throws InterruptedException {
    long startTime = System.currentTimeMillis();
    Thread.sleep(1000 * 5);
    long spendTime = System.currentTimeMillis() - startTime;
    return new AsyncResult<>(
        Thread.currentThread().getName() + ", value: " + str + ", time spent: " + spendTime);
  }

  @Async
  public Future<String> method3(String str) throws InterruptedException {
    long startTime = System.currentTimeMillis();
    Thread.sleep(1000 * 15);
    long spendTime = System.currentTimeMillis() - startTime;
    return new AsyncResult<>(
        Thread.currentThread().getName() + ", value: " + str + ", time spent: " + spendTime);
  }
}
