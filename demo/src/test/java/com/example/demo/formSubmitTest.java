package com.example.demo;

import com.example.demo.model.FAQ;
import com.example.demo.repository.FAQRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class formSubmitTest {

    @Autowired
    FAQRepository faqRepository;

    @Test
    public void formSubmit(){
        for(int i=0; i<100; i++){
            FAQ faq = new FAQ();
            faq.setTitle("게시글 테스트 " + i);
            faq.setContent("게시글 테스트 게시글 테스트 " + i);
            faq.setUsername("테스트");

            faqRepository.save(faq);
            System.out.println(faq);
        }
    }

}

