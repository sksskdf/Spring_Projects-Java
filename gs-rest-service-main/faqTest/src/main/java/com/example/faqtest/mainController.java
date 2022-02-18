package com.example.faqtest;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import javax.validation.Valid;

@Controller
public class mainController implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/results").setViewName("results");
    }

    @GetMapping("/")
    public String showForm(FAQ personForm) {
        return "index";
    }

    @PostMapping("/")
    public String checkPersonInfo(@Valid FAQ personForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "index";
        }

        return "redirect:/";
    }
}
