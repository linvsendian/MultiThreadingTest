package com.example.multithreadingtest.configuration;

import java.lang.reflect.Method;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

/**
 * MyAsyncUncaughtExceptionHandler.
 *
 * @Description TODO
 * @Date 26/04/2022 10:17
 * @Created by Qinxiu Wang
 */
public class MyAsyncUncaughtExceptionHandler implements AsyncUncaughtExceptionHandler {

  @Override
  public void handleUncaughtException(Throwable ex, Method method, Object... params) {
    // handle exception
  }
}
