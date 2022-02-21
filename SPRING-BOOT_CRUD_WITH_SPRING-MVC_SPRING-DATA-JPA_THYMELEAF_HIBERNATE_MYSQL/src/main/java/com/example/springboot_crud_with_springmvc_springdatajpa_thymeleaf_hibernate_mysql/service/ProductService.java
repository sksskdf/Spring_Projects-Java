package com.example.springboot_crud_with_springmvc_springdatajpa_thymeleaf_hibernate_mysql.service;

import com.example.springboot_crud_with_springmvc_springdatajpa_thymeleaf_hibernate_mysql.model.Product;
import com.example.springboot_crud_with_springmvc_springdatajpa_thymeleaf_hibernate_mysql.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository repo;

    public List<Product> listAll() {
        return repo.findAll();
    }

    public void save(Product product) {
        repo.save(product);
    }

    public Product get(long id) {
        return repo.findById(id).get();
    }

    public void delete(long id) {
        repo.deleteById(id);
    }
}
