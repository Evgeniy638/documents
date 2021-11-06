package com.evgeniy638.documents.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @GetMapping("/home")
    public String index(Model model, HttpServletRequest request) {
        model.addAttribute("login", request.getUserPrincipal().getName());
        return "home";
    }
}
