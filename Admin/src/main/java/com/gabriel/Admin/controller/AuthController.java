package com.gabriel.Admin.controller;

import com.gabriel.Backend.dto.AdminDto;
import com.gabriel.Backend.model.Admin;
import com.gabriel.Backend.service.impl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthController {
    @Autowired
    private AdminServiceImpl adminService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Login");
        return "login";
    }

    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("title", "Home");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        }
        return "index";
    }

    @GetMapping("/register")

    public String register(Model model) {
        model.addAttribute("title", "Registro");
        model.addAttribute("admin", new AdminDto());
        return "register";

    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {
        model.addAttribute("title", "Recuperação Senha");
        return "forgot-password";

    }
}

