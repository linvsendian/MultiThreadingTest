package com.example.multithreadingtest.repository;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * EmptyEntity.
 *
 * @Description EmptyEntity for custom query usage.
 * @Date 14/02/2022 16:13
 * @Created by Qinxiu Wang
 */
@Entity
public class EmptyEntity implements Serializable {

  @Id
  private long id;
}
