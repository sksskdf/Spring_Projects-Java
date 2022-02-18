package com.example.demo.model;

import javax.persistence.*;
import javax.validation.constraints.*;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Data
public class FAQ {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 10)
    private String username;

    @Column(nullable = false,length = 30)
    private String title;

    @Column(nullable = false,length = 200)
    private String content;

    @Column(nullable = true,length = 100)
    private String filename;

    @Column(nullable = true,length = 100)
    private String fileurl;

}
