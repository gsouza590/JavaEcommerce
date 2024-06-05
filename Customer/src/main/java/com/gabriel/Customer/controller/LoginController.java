package com.gabriel.Customer.controller;

import com.gabriel.Backend.dto.CustomerDto;
import com.gabriel.Backend.model.Customer;
import com.gabriel.Backend.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final CustomerService customerService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("title", "Login");
        model.addAttribute("page", "Home");
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("title", "Registro");
        model.addAttribute("page", "Registro");
        model.addAttribute("customerDto", new CustomerDto());
        return "register";
    }

    @PostMapping("/do-register")
    public String registerCustomer(@Valid @ModelAttribute("customerDto") CustomerDto customerDto,
                                   BindingResult result,
                                   Model model) {
        if (result.hasErrors()) {
            model.addAttribute("customerDto", customerDto);
            return "register";
        }

        String username = customerDto.getUsername();
        Customer existingCustomer = customerService.findByUsername(username);
        if (existingCustomer != null) {
            model.addAttribute("customerDto", customerDto);
            model.addAttribute("error", "Email já registrado!");
            return "register";
        }

        if (!customerDto.getPassword().equals(customerDto.getRepeatPassword())) {
            model.addAttribute("error", "As senhas não coincidem!");
            model.addAttribute("customerDto", customerDto);
            return "register";
        }

        try {
            customerDto.setPassword(bCryptPasswordEncoder.encode(customerDto.getPassword()));
            customerService.save(customerDto);
            model.addAttribute("success", "Registro realizado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Erro no servidor, tente novamente!");
        }

        return "register";
    }
}
