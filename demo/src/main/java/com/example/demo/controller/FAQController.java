package com.example.demo.controller;

import com.example.demo.utils.FileStorageService;
import com.example.demo.utils.MyFileNotFoundException;
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
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.FAQ;
import com.example.demo.repository.FAQRepository;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@Controller
@RequestMapping("/faq")
@RequiredArgsConstructor
public class FAQController {
    private static final String FILE_SERVICE_STORAGE_DIRECTORY = "files";

	@Autowired
	private FAQRepository faqRepository;

    @Autowired
    FileStorageService fileStorageService;


    @GetMapping(FileStorageService.FILE_DOWNLOAD_API_ENDPOINT)
    public ResponseEntity<Resource> resouceFileDownload(@PathVariable @RequestParam(required = true) String filename, HttpServletRequest request) throws IOException {
        Resource resource = fileStorageService.loadFileAsResource(FILE_SERVICE_STORAGE_DIRECTORY, filename);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            throw new MyFileNotFoundException("Invalid content type!");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
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
    public String FAQSubmit(FAQ faq,@RequestParam("files") MultipartFile file) throws IOException {
        if(file.isEmpty()) {
            faqRepository.save(faq);
            return "redirect:/faq/list";
        }
        String filename = file.getOriginalFilename();
        int idx = filename.lastIndexOf(".");
        String filename_ = filename.substring(0,idx);
        String fileName = fileStorageService.storeMultipartFile(file, FILE_SERVICE_STORAGE_DIRECTORY, filename_ + "");
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/faq/" + FileStorageService.FILE_DOWNLOAD_API_ENDPOINT + "/")
                .path(fileName)
                .toUriString();

        faq.setFilename(file.getOriginalFilename());
        faq.setFileurl(fileDownloadUri);
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
