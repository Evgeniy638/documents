package com.evgeniy638.documents.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller()
@RequestMapping("/files")
public class FileController {
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, Model model) {
        model.addAttribute("file", file);
        return "index";
    }
}
