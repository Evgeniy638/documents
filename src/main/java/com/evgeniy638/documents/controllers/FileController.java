package com.evgeniy638.documents.controllers;

import com.evgeniy638.documents.dto.FileDTO;
import com.evgeniy638.documents.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller()
@RequestMapping("/files")
public class FileController {
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public String upload(@ModelAttribute FileDTO fileDTO, HttpServletRequest request) {
        fileService.save(fileDTO, request.getUserPrincipal().getName());
        return "redirect:" + request.getHeader("Referer");
    }

    @GetMapping("/sent")
    public String index(Model model, HttpServletRequest request) {
        String username = request.getUserPrincipal().getName();

        model.addAttribute("username", username);
        model.addAttribute("files", fileService.getSentFiles(username));
        return "sentFiles";
    }
}
