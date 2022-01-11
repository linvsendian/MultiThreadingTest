package com.example.multithreadingtest.model;

/**
 * IUser.
 *
 * @Description TODO
 * @Date 11/01/2022 20:26
 * @Created by Qinxiu Wang
 */
public interface IUserProjection {

  Long getId();

  String getName();

  String getEmail();

  default String toStringObj() {
    return "{"
        + "id: " + getId() + ", "
        + "name: " + getName() + ", "
        + "email: " + getEmail()
        + "}";
  }
}
