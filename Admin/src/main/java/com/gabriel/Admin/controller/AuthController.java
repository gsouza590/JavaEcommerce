package com.gabriel.Admin.controller;

import com.gabriel.Backend.dto.AdminDto;
import com.gabriel.Backend.model.Admin;
import com.gabriel.Backend.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;


@Controller
@RequiredArgsConstructor
public class AuthController {
    private final AdminService adminService;
    private final BCryptPasswordEncoder passwordEncoder;


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
        model.addAttribute("adminDto", new AdminDto());
        return "register";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(Model model) {
        model.addAttribute("title", "Recuperação Senha");
        return "forgot-password";

    }

    @PostMapping("/register-new")
    public String addNewAdmin(@Valid @ModelAttribute("adminDto") AdminDto adminDto, BindingResult result, Model model) {

        try {
            if (result.hasErrors()) {
                model.addAttribute("adminDto", adminDto);
                return "register";
            }

            String username = adminDto.getUsername();
            Admin admin = adminService.findByUsername(username);

            if (admin != null) {
                model.addAttribute("adminDto", adminDto);
                System.out.println("Admin não nulo");
                model.addAttribute("emailError", "Seu email já foi registrado");
                return "register";
            }

            if (adminDto.getPassword().equals(adminDto.getRepeatPassword())) {
                adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));
                adminService.save(adminDto);
                System.out.println("Sucesso");
                model.addAttribute("success", "Registrado com Sucesso!");
                model.addAttribute("adminDto", adminDto);
            } else {
                model.addAttribute("adminDto", adminDto);
                model.addAttribute("passwordError", "Sua senha está errada. Tente Novamente!");
                System.out.println("As senhas não coincidem!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errors", "Erro de Servidor!");
        }
        return "register";

    }

    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();
        Admin admin = adminService.findByUsername(username);

        model.addAttribute("adminDto", admin);
        model.addAttribute("title", "Minha Conta");
        model.addAttribute("page", "Minha Conta");

        return "profile";
    }

    @GetMapping("/update-profile")
    public String showUpdateProfileForm(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();
        AdminDto adminDto = adminService.getAdmin(username);

        model.addAttribute("adminDto", adminDto);
        model.addAttribute("title", "Atualizar Perfil");
        return "profile";
    }


    @PostMapping("/update-profile")
    public String updateProfile(@Valid @ModelAttribute("adminDto") AdminDto adminDto,
                                BindingResult result, Model model, Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            model.addAttribute("adminDto", adminDto);
            return "profile";
        }

        try {
            String username = principal.getName();
            adminDto.setUsername(username);

            adminService.update(adminDto);

            model.addAttribute("success", "Perfil atualizado com sucesso!");
            model.addAttribute("adminDto", adminDto);
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Erro de Servidor ao atualizar perfil");
        }

        return "redirect:/profile";
    }

}


