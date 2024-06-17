package com.gabriel.Admin.controller;

import com.gabriel.Backend.dto.AdminDto;
import com.gabriel.Backend.model.Admin;
import com.gabriel.Backend.service.AdminService;
import com.gabriel.Backend.service.exceptions.Admin.AdminAlreadyExistsException;
import com.gabriel.Backend.service.exceptions.Admin.AdminNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AdminService adminService;
    private final BCryptPasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Login");
        return "login";
    }

    @GetMapping("/index")
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
    public String addNewAdmin(@Valid @ModelAttribute("adminDto") AdminDto adminDto, BindingResult result,
                              Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("adminDto", adminDto);
            return "register";
        }

        if (result.hasErrors()) {
            setupModelForRegister(model, adminDto);
            return "register";
        }

        if (!adminDto.getPassword().equals(adminDto.getRepeatPassword())) {
            setupModelForRegister(model, adminDto);
            model.addAttribute("passwordError", "Sua senha está errada. Tente Novamente!");
            logger.info("As senhas não coincidem!");
            return "register";
        }
        try {
<<<<<<< HEAD
            String username = adminDto.getUsername();
            if (adminService.existsByUsername(username)) {
                model.addAttribute("adminDto", adminDto);
                model.addAttribute("emailError", "Seu email já foi registrado");
                return "register";
            }

            if (adminDto.getPassword().equals(adminDto.getRepeatPassword())) {
                adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));
                adminService.save(adminDto);
                redirectAttributes.addFlashAttribute("success", "Registrado com Sucesso!");
                return "redirect:/register";
            } else {
                model.addAttribute("passwordError", "Suas senhas não coincidem. Tente novamente!");
                return "register";
            }
=======
            adminDto.setPassword(passwordEncoder.encode(adminDto.getPassword()));
            adminService.save(adminDto);
            setupModelForRegister(model, adminDto);
            model.addAttribute("success", "Registrado com Sucesso!");
            logger.info("Sucesso");
        } catch (AdminAlreadyExistsException e) {
            setupModelForRegister(model, adminDto);
            model.addAttribute("emailError", e.getMessage());
            logger.info("Admin não nulo");
>>>>>>> a972b13e25d0fadc3c043290cc30af4ba9281dc4
        } catch (Exception e) {
            logger.error("Erro de Servidor", e);
            model.addAttribute("errors", "Erro de Servidor!");
            return "register";
        }
<<<<<<< HEAD
=======

        return "register";

>>>>>>> a972b13e25d0fadc3c043290cc30af4ba9281dc4
    }

    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        String username = principal.getName();

        try {
            Admin admin = adminService.findByUsername(username);
            model.addAttribute("adminDto", admin);
            model.addAttribute("title", "Minha Conta");
            model.addAttribute("page", "Minha Conta");

            return "profile";
        } catch (AdminNotFoundException e) {
            logger.error(e.getMessage());
            return "redirect:/login";
        }
    }

    @GetMapping("/update-profile")
    public String showUpdateProfileForm(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
<<<<<<< HEAD
        AdminDto adminDto = adminService.getAdmin(username);
=======
        try {
            AdminDto adminDto = adminService.getAdmin(username);
            model.addAttribute("adminDto", adminDto);
            model.addAttribute("title", "Atualizar Perfil");
            return "profile";
        } catch (AdminNotFoundException e) {
            logger.error(e.getMessage());
            return "redirect:/login";
        }
>>>>>>> a972b13e25d0fadc3c043290cc30af4ba9281dc4

    }

    @PostMapping("/update-profile")
<<<<<<< HEAD
    public String updateProfile(@Valid @ModelAttribute("adminDto") AdminDto adminDto,
                                BindingResult result, Model model, Principal principal,
                                RedirectAttributes redirectAttributes) {
=======
    public String updateProfile(@Valid @ModelAttribute("adminDto") AdminDto adminDto, BindingResult result, Model model, Principal principal) {
>>>>>>> a972b13e25d0fadc3c043290cc30af4ba9281dc4
        if (principal == null) {
            logger.warn("Principal is null, redirecting to login.");
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            logger.info("Validation errors found in form submission.");
            model.addAttribute("adminDto", adminDto);
            return "profile";
        }

        try {
            String username = principal.getName();
            adminDto.setUsername(username);
<<<<<<< HEAD
            adminService.update(adminDto);

            redirectAttributes.addFlashAttribute("success", "Perfil atualizado com sucesso!");
            return "redirect:/profile";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Erro de Servidor ao atualizar perfil");
            return "redirect:/profile";
=======
            logger.info("Updating profile for user: {}", username);

            adminService.update(adminDto);
            model.addAttribute("success", "Perfil atualizado com sucesso!");
            model.addAttribute("adminDto", adminDto);
        } catch (AdminNotFoundException e) {
            logger.error("Admin not found: {}", e.getMessage());
            model.addAttribute("error", e.getMessage());
            return "profile";
        } catch (Exception e) {
            logger.error("Erro de Servidor ao atualizar perfil", e);
            model.addAttribute("error", "Erro de Servidor ao atualizar perfil");
>>>>>>> a972b13e25d0fadc3c043290cc30af4ba9281dc4
        }
    }
<<<<<<< HEAD
=======


    private void setupModelForRegister(Model model, AdminDto adminDto) {
        model.addAttribute("adminDto", adminDto);
        model.addAttribute("title", "Registro");
    }
>>>>>>> a972b13e25d0fadc3c043290cc30af4ba9281dc4
}
