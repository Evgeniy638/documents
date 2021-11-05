package com.evgeniy638.documents.controllers;

import com.evgeniy638.documents.dto.GroupDTO;
import com.evgeniy638.documents.dto.InstituteDTO;
import com.evgeniy638.documents.dto.StudentDTO;
import com.evgeniy638.documents.services.GroupService;
import com.evgeniy638.documents.services.InstituteService;
import com.evgeniy638.documents.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final InstituteService instituteService;
    private final GroupService groupService;
    private final UserService userService;

    @GetMapping
    public String admin(Model model) {
        model.addAttribute("newInstitute", new InstituteDTO());
        model.addAttribute("newGroup", new GroupDTO());
        model.addAttribute("newStudent", new StudentDTO());

        model.addAttribute("institutes", instituteService.getInstitutes());

        return "admin";
    }

    @PostMapping("/institute")
    public String createInstitute(@ModelAttribute("newInstitute") InstituteDTO instituteDTO) {
        instituteService.createInstitute(instituteDTO);
        return "redirect:/admin";
    }

    @PostMapping("/group")
    public String createGroup(@ModelAttribute("newGroup") GroupDTO groupDTO) {
        groupService.createGroup(groupDTO);
        return "redirect:/admin";
    }

    @PostMapping("/student")
    public String createStudent(@ModelAttribute("newStudent") StudentDTO studentDTO) {
        userService.createStudent(studentDTO);
        return "redirect:/admin";
    }

    @PostMapping("/file")
    public String uploadFile() {
        return "redirect:/admin";
    }
}
