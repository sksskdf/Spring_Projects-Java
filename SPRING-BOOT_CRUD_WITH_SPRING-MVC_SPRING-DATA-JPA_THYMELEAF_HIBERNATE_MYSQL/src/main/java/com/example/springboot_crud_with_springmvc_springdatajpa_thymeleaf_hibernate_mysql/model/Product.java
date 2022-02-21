package com.example.springboot_crud_with_springmvc_springdatajpa_thymeleaf_hibernate_mysql.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false,length = 45)
    private String name;
    @Column(nullable = false,length = 45)
    private String brand;
    @Column(nullable = false,length = 45)
    private String madein;
    @Column(nullable = false)
    private float price;
}
