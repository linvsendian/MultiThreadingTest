package com.example.multithreadingtest.repository;

import com.example.multithreadingtest.model.IUserProjection;
import com.example.multithreadingtest.model.User;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

/**
 * userRepository.
 *
 * @Description TODO
 * @Date 11/01/2022 20:20
 * @Created by Qinxiu Wang
 */
public interface IUserRepository extends JpaRepository<User, Long> {


  List<User> findByEmail(String email);

  @Async
  CompletableFuture<List<User>> findAllByEmail(String email);

  @Query(value = ""
      + "SELECT utb.id, utb.name, utb.email "
      + "FROM user_tb utb WHERE email = :email ", nativeQuery = true)
  List<IUserProjection> findByEmailNative(String email);

  @Query(value = ""
      + "SELECT utb.id, utb.name, utb.email "
      + "FROM user_tb utb WHERE email = :email ", nativeQuery = true)
  @Async
  CompletableFuture<List<User>> findByEmailNativeAsync(String email);
}


