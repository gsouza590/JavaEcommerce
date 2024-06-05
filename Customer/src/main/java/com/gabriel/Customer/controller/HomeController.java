package com.gabriel.Customer.controller;

import com.gabriel.Backend.model.Customer;
import com.gabriel.Backend.model.ShoppingCart;
import com.gabriel.Backend.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CustomerService customerService;

    @GetMapping(value = {"/", "index"})
    public String home(Model model, Principal principal, HttpSession session) {
        model.addAttribute("title", "Home");
        model.addAttribute("page", "Home");
        if (principal != null) {
            setupSessionForAuthenticatedUser(session, principal);
        }
        return "home";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        model.addAttribute("title", "Contato");
        model.addAttribute("page", "Contato");
        return "contact";
    }

    private void setupSessionForAuthenticatedUser(HttpSession session, Principal principal) {
        Customer customer = customerService.findByUsername(principal.getName());
        session.setAttribute("username", customer.getFirstName() + " " + customer.getLastName());
        ShoppingCart shoppingCart = customer.getShoppingCart();
        if (shoppingCart != null) {
            session.setAttribute("totalItems", shoppingCart.getTotalItems());
        }
    }
}
