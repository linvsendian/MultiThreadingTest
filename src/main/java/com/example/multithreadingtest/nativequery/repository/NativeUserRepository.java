package com.example.multithreadingtest.nativequery.repository;

import com.example.multithreadingtest.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface NativeUserRepository extends JpaRepository<User, Long> {

    @Query(value = ""
            + "SELECT utb.id, utb.name, utb.email "
            + "FROM user_tb utb WHERE email = :email ", nativeQuery = true)
    List<User> findByEmail(String email);

}


