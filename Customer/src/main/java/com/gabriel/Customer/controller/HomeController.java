package com.gabriel.Customer.controller;

import com.gabriel.Backend.model.Customer;
import com.gabriel.Backend.model.ShoppingCart;
import com.gabriel.Backend.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class HomeController {
    @Autowired
    private CustomerService customerService;


    @GetMapping(value = {"/","index"})
    public String home(Model model, Principal principal, HttpSession session){

        model.addAttribute("title", "Home");
        model.addAttribute("page","Home");
        if(principal!=null){
            Customer customer = customerService.findByUsername(principal.getName());
            session.setAttribute("username", customer.getFirstName()+ " " + customer.getFirstName());
            ShoppingCart shoppingCart = customer.getShoppingCart();
            if(shoppingCart!= null){
                session.setAttribute("totalItems" ,shoppingCart.getTotalItems());
            }
        }
        return "home";
    }
    }

