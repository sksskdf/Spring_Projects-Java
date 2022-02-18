package com.example.fileuploaddemo.repository;

import com.example.fileuploaddemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
