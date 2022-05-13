package com.example.multithreadingtest.configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * AsyncConfig.
 *
 * @Description TODO
 * @Date 26/04/2022 10:14
 * @Created by Qinxiu Wang
 */
@Configuration
@EnableAsync
public class ThreadExecutorConfig implements AsyncConfigurer {

  @Override
  public Executor getAsyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    // 设置核心线程数
    executor.setCorePoolSize(7);
    // 设置最大线程数
    executor.setMaxPoolSize(7);
    // 设置队列容量
    executor.setQueueCapacity(20);
    // 设置线程活跃时间（秒）
    executor.setKeepAliveSeconds(60);
    // 设置默认线程名称
    executor.setThreadNamePrefix("MY-Thread-");
    // 设置拒绝策略
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    // 等待所有任务结束后再关闭线程池
    executor.setWaitForTasksToCompleteOnShutdown(true);
    executor.initialize();
    return executor;
  }

  @Override
  public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
    return new MyAsyncUncaughtExceptionHandler();
  }
}