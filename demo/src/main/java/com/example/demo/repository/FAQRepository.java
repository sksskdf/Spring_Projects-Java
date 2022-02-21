package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.FAQ;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface FAQRepository extends JpaRepository<FAQ, Long>{
	Page<FAQ> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
	Optional<FAQ> findById(Long id);

	/*@Modifying
	@Query("UPDATE faq f SET f.reply_username = :reply_username,f.reply_content =:reply_content WHERE f.id = :id")
	int updateReply(String reply_username,String reply_content, Long id);*/
}
