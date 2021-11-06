package com.evgeniy638.documents.controllers;

import com.evgeniy638.documents.dto.FileDTO;
import com.evgeniy638.documents.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

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

    @DeleteMapping("/creator/{id}")
    public String deleteCreator(@PathVariable("id") UUID uuid, HttpServletRequest request) {
        fileService.deleteCreator(uuid);
        return "redirect:" + request.getHeader("Referer");
    }

    @DeleteMapping("/user/{userId}/{fileId}")
    public String deleteFromUser(
            @PathVariable("userId") int userId,
            @PathVariable("fileId") UUID fileId,
            HttpServletRequest request) {
        fileService.deleteFromUser(userId, fileId);
        return "redirect:" + request.getHeader("Referer");
    }

    @DeleteMapping("/group/{groupId}/{fileId}")
    public String deleteFromGroup(
            @PathVariable("groupId") int groupId,
            @PathVariable("fileId") UUID fileId,
            HttpServletRequest request) {
        fileService.deleteFromGroup(groupId, fileId);
        return "redirect:" + request.getHeader("Referer");
    }

    @DeleteMapping("/institute/{instituteId}/{fileId}")
    public String deleteFromInstitute(
            @PathVariable("instituteId") int instituteId,
            @PathVariable("fileId") UUID fileId,
            HttpServletRequest request) {
        fileService.deleteFromInstitute(instituteId, fileId);
        return "redirect:" + request.getHeader("Referer");
    }
}
