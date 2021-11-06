package com.evgeniy638.documents.controllers;

import com.evgeniy638.documents.dto.ChangePasswordDTO;
import com.evgeniy638.documents.modules.Role;
import com.evgeniy638.documents.modules.User;
import com.evgeniy638.documents.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Controller
public class IndexController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public IndexController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/home")
    public String index(Model model, HttpServletRequest request) {
        model.addAttribute("login", request.getUserPrincipal().getName());
        return "index";
    }

    @GetMapping("/change-pass")
    public String registration(Model model) {
        model.addAttribute("user", new ChangePasswordDTO());
        return "changePass";
    }

    @PostMapping("/change-pass")
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
