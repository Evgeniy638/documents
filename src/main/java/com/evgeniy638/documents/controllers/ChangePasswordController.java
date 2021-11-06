package com.evgeniy638.documents.controllers;

import com.evgeniy638.documents.dto.ChangePasswordDTO;
import com.evgeniy638.documents.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/change-pass")
@RequiredArgsConstructor
public class ChangePasswordController {
    private final UserService userService;

    @GetMapping
    public String registration(Model model) {
        model.addAttribute("user", new ChangePasswordDTO());
        return "changePass";
    }

    @PostMapping
    public String addUser(@ModelAttribute("user") ChangePasswordDTO user, Model model) {
        try {
            userService.changePassword(user);
        } catch (Error e) {
            model.addAttribute("error", e.getMessage());
            return "changePass";
        }

        return "redirect:/login";
    }
}
