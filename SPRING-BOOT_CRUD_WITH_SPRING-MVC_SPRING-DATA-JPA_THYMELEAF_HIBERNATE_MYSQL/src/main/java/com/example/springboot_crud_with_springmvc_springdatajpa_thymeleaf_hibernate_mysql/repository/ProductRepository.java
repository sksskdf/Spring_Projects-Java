package com.example.springboot_crud_with_springmvc_springdatajpa_thymeleaf_hibernate_mysql.repository;

import com.example.springboot_crud_with_springmvc_springdatajpa_thymeleaf_hibernate_mysql.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
