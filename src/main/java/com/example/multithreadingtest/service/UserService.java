package com.example.multithreadingtest.service;

import com.example.multithreadingtest.model.User;
import com.example.multithreadingtest.repository.IUserRepository;
import java.util.concurrent.Future;
import java.util.stream.IntStream;
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


  public void saveUsers() {
    int maxSize = 100000;
    IntStream.rangeClosed(1, maxSize).forEach(
        x -> {
          User tempUser = User.builder().id(x).name("user_" + x).email("email").build();
          userRepository.save(tempUser);
        }
    );
  }

  @Async
  public Future<String> method(String str) {
    long startTime = System.currentTimeMillis();
    userRepository.findByEmail("email");
    long spendTime = System.currentTimeMillis() - startTime;
    return new AsyncResult<>(
        Thread.currentThread().getName() + ", value: " + str + ", time spent: " + spendTime);
  }

  @Async
  public Future<String> nativeMethod(String str) {
    long startTime = System.currentTimeMillis();
    userRepository.findByEmailNative("email");
    long spendTime = System.currentTimeMillis() - startTime;
    return new AsyncResult<>(
        Thread.currentThread().getName() + ", value: " + str + ", time spent: " + spendTime);
  }

}
