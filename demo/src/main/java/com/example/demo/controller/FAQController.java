package com.example.demo.controller;

import com.example.demo.utils.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.FAQ;
import com.example.demo.repository.FAQRepository;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


@Controller
@RequestMapping("/faq")
@RequiredArgsConstructor
public class FAQController {

	@Autowired
	private FAQRepository faqRepository;

    @Autowired
    ResourceLoader resourceLoader;

    @GetMapping("/download")
    public ResponseEntity<Resource> resouceFileDownload(@RequestParam String filename) throws IOException {
        try {
            Resource resource = resourceLoader.getResource("classpath:static/files/"+filename);
            File file = resource.getFile();	//파일이 없는 경우 fileNotFoundException error가 난다.

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename=\"" + filename + "\";")
                    .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()))	//파일 사이즈 설정
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM.toString())	//바이너리 데이터로 받아오기 설정
                    .body(resource);	//파일 넘기기
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(null);
        } catch (Exception e ) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

	@GetMapping("/list")
	public String faq(Model model,@PageableDefault(size = 8, sort = "id",  direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false,defaultValue = "") String searchText){
		Page<FAQ> faqs = faqRepository.findByTitleContainingOrContentContaining(searchText,searchText,pageable);
        int startPage = Math.max(1,faqs.getPageable().getPageNumber() - 4);
        int endPage = Math.min(faqs.getTotalPages(),faqs.getPageable().getPageNumber()+4);
        boolean emptyresult = false;
        if(faqs.getTotalPages() == 0){
            emptyresult = true;
        }
        model.addAttribute("emptyresult",emptyresult);
        model.addAttribute("startpage",startPage);
        model.addAttribute("endpage",endPage);
        model.addAttribute("faqs",faqs);
        return "faq/list";
	}

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id){
        if(id==null){
            //글쓰기
            model.addAttribute("faq",new FAQ());
        }else {
            //수정
            FAQ faq = faqRepository.findById(id).orElse(null);
            model.addAttribute("faq",faq);
        }
        return "faq/form";
    }

    @PostMapping("/form")
    public String FAQSubmit(FAQ faq,@RequestParam("files") MultipartFile files) throws IOException {
        if(files.isEmpty()) {
            faqRepository.save(faq);
            return "redirect:/faq/list";
        }
        System.out.println(files.getName());
        String filename = StringUtils.cleanPath(files.getOriginalFilename());
        String uploadDir = "src/main/resources/static/files/";
        faq.setFilename(filename);
        faq.setFileurl(uploadDir);
        FileUploadUtil.saveFile(uploadDir, filename, files);
        faqRepository.save(faq);


        return "redirect:/faq/list";
    }

    @GetMapping("/detail")
    public String detail(Model model,@RequestParam(required = true) Long id){
        FAQ faq = faqRepository.findById(id).orElse(null);
        model.addAttribute("faq",faq);
        return "faq/detail";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) Long id){
        faqRepository.deleteById(id);
        return "redirect:/faq/list";
    }
}
