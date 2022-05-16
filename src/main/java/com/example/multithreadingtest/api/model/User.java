package com.example.multithreadingtest.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * user.
 *
 * @Description TODO
 * @Date 11/01/2022 20:14
 * @Created by Qinxiu Wang
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_tb")
public class User implements Serializable {

    private static final long serialVersionUID = -2582224494389745630L;

    @Id
    private long id;

    private String name;

    private String email;
}
