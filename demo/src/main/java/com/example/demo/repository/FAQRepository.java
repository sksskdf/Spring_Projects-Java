package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.FAQ;

import java.util.Optional;

public interface FAQRepository extends JpaRepository<FAQ, Long>{
	Page<FAQ> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
	Optional<FAQ> findById(Long id);

}
