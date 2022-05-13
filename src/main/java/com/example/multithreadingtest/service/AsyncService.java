package com.example.multithreadingtest.service;

import com.example.multithreadingtest.repository.ILegacyRepository;
import com.example.multithreadingtest.repository.IUserRepository;
import java.util.Arrays;
import java.util.List;
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

  @Autowired
  private ILegacyRepository legacyRepository;

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

  @Async
  public Future<String> method_legacy(String str) {
    long startTime = System.currentTimeMillis();

    String sinceDate = "01/04/2022";
    String upToDate = "31/05/2022";
    String aspm = "AB - CARLOS ARAU";
    List<String> productCodes = Arrays.asList("BV", "T05", "T03", "BPC", "ACC");

    legacyRepository.findProductIncentiveMapperByAspm(sinceDate, upToDate, sinceDate, aspm, 1, 0,
        productCodes);
    long spendTime = System.currentTimeMillis() - startTime;
    return new AsyncResult<>(
        Thread.currentThread().getName() + ", value: " + str + ", time spent: " + spendTime);
  }
}
