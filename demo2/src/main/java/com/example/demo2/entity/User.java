package com.example.demo2.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,length = 45,unique = true)
    private String userid;
    @Column(nullable = false,length = 64)
    private String password;
    @Column(name = "name",nullable = false,length = 20)
    private String name;
    


}
