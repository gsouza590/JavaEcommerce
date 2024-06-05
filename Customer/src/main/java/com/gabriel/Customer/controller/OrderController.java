package com.gabriel.Customer.controller;

import com.gabriel.Backend.dto.CustomerDto;
import com.gabriel.Backend.model.*;
import com.gabriel.Backend.service.CityService;
import com.gabriel.Backend.service.CountryService;
import com.gabriel.Backend.service.CustomerService;
import com.gabriel.Backend.service.OrderService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final CustomerService customerService;
    private final CityService cityService;
    private final CountryService countryService;
    private final OrderService orderService;

    @GetMapping("/order")
    public String getOrders(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Customer customer = customerService.findByUsername(principal.getName());
        List<Order> orderList = customer.getOrders();
        model.addAttribute("orders", orderList);
        model.addAttribute("title", "Pedido");
        model.addAttribute("page", "Pedido");
        return "order";
    }

    @PostMapping("/add-order")
    @Transactional
    public String createOrder(Principal principal, Model model, HttpSession session) {
        if (principal == null) {
            return "redirect:/login";
        }
        Customer customer = customerService.findByUsername(principal.getName());
        ShoppingCart cart = customer.getShoppingCart();
        Order order = orderService.save(cart);
        session.removeAttribute("totalItems");
        model.addAttribute("order", order);
        model.addAttribute("title", "Detalhes Pedido");
        model.addAttribute("page", "Detalhes Pedido");
        model.addAttribute("success", "Pedido adicionado com Sucesso!!");
        return "order-detail";
    }

    @GetMapping("/check-out")
    public String checkOut(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }
        CustomerDto customer = customerService.getCustomer(principal.getName());
        if (customer.getAddress() == null || customer.getCity() == null || customer.getPhoneNumber() == null) {
            model.addAttribute("information", "Necessário atualizar os dados pessoais antes de continuar!");
            List<Country> countryList = countryService.findAll();
            List<City> cities = cityService.findAll();
            model.addAttribute("customer", customer);
            model.addAttribute("cities", cities);
            model.addAttribute("countries", countryList);
            model.addAttribute("title", "Perfil");
            model.addAttribute("page", "Perfil");
            return "customer-information";
        }
        ShoppingCart cart = customerService.findByUsername(principal.getName()).getShoppingCart();
        model.addAttribute("customer", customer);
        model.addAttribute("title", "Check-Out");
        model.addAttribute("page", "Check-Out");
        model.addAttribute("shoppingCart", cart);
        model.addAttribute("grandTotal", cart.getTotalItems());
        return "checkout";
    }

    @RequestMapping(value = "/cancel-order/{id}", method = {RequestMethod.PUT, RequestMethod.GET})
    public String cancelOrder(@PathVariable Long id, RedirectAttributes attributes) {
        orderService.cancelOrder(id);
        attributes.addFlashAttribute("success", "Pedido cancelado com sucesso!");
        return "redirect:/order";
    }
}
