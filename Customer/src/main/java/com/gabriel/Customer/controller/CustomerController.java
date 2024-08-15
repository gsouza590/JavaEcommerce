package com.gabriel.Customer.controller;

import com.gabriel.Backend.dto.CustomerDto;
import com.gabriel.Backend.model.City;
import com.gabriel.Backend.model.Country;
import com.gabriel.Backend.service.CityService;
import com.gabriel.Backend.service.CountryService;
import com.gabriel.Backend.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class CustomerController {
    private final CustomerService customerService;
    private final CountryService countryService;
    private final CityService cityService;
    private final BCryptPasswordEncoder passwordEncoder;

    public CustomerController(CustomerService customerService, CountryService countryService, CityService cityService, BCryptPasswordEncoder passwordEncoder) {
        this.customerService = customerService;
        this.countryService = countryService;
        this.cityService = cityService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        CustomerDto customer = customerService.getCustomer(username);
        List<Country> countryList = countryService.findAll();
        List<City> cities = cityService.findAll();
        model.addAttribute("customer", customer);
        model.addAttribute("cities", cities);
        model.addAttribute("countries", countryList);
        model.addAttribute("title", "Minha Conta");
        model.addAttribute("page", "Minha Conta");
        return "customer-information";

    }


    @PostMapping("/update-profile")
    public String updateProfile(@Valid @ModelAttribute("customer") CustomerDto customerDto,
                                BindingResult result,
                                RedirectAttributes attributes,
                                Model model,
                                Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        if (result.hasErrors()) {
            return "customer-information";
        }

        // Atualiza as informações com base no DTO
        customerService.update(customerDto);

        // Obtém as informações atualizadas do cliente
        CustomerDto customerUpdate = customerService.getCustomer(principal.getName());

        attributes.addFlashAttribute("success", "Atualização realizada com sucesso!");
        model.addAttribute("customer", customerUpdate);
        return "redirect:/profile";
    }


    @GetMapping("/change-password")
    public String changePassword(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("title", "Mudar Senha");
        model.addAttribute("page", "Mudar Senha");
        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePass(@RequestParam("oldPassword") String oldPassword,
                             @RequestParam("newPassword") String newPassword,
                             @RequestParam("repeatNewPassword") String repeatPassword,
                             RedirectAttributes attributes,
                             Model model,
                             Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        CustomerDto customer = customerService.getCustomer(principal.getName());
        if (passwordEncoder.matches(oldPassword, customer.getPassword())) {
            if (!passwordEncoder.matches(newPassword, oldPassword) &&
                    !passwordEncoder.matches(newPassword, customer.getPassword()) &&
                    repeatPassword.equals(newPassword) && newPassword.length() >= 5) {

                customer.setPassword(passwordEncoder.encode(newPassword));
                customerService.changePass(customer);
                attributes.addFlashAttribute("success", "Sua senha foi mudada com sucesso!");
                return "redirect:/profile";
            } else {
                model.addAttribute("message", "As novas senhas não coincidem ou são inválidas.");
            }
        } else {
            model.addAttribute("message", "Sua senha antiga está incorreta.");
        }
        return "change-password";
    }
}



