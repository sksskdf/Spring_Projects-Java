package com.example.fileuploaddemo.controller;

import com.example.fileuploaddemo.entity.User;
import com.example.fileuploaddemo.repository.UserRepository;
import com.example.fileuploaddemo.utils.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;


@Controller
public class userController {

    @Autowired
    private UserRepository repo;

    @GetMapping("/form")
    public String formView(){
        return "userForm";
    }

    @PostMapping("/form")
    public String formSubmit(User user, @RequestParam("image")MultipartFile multipartFile, RedirectAttributes attributes) throws IOException {
        if (multipartFile.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/form";
        }
        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        user.setPhotos(filename);

        User savedUser = repo.save(user);

        String uploadDir = "src/main/resources/static/images/";

        FileUploadUtil.saveFile(uploadDir, filename, multipartFile);

        return "redirect:/user";
    }

    @GetMapping("/user")
    public String userView(Model model){
        List<User> user = repo.findAll();
        model.addAttribute("users",user);
        return "userView";
    }
}
