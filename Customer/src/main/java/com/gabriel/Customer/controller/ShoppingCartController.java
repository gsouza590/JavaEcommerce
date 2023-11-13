package com.gabriel.Customer.controller;

import com.gabriel.Backend.dto.ProductDto;
import com.gabriel.Backend.model.Customer;
import com.gabriel.Backend.model.ShoppingCart;
import com.gabriel.Backend.service.CustomerService;
import com.gabriel.Backend.service.ProductService;
import com.gabriel.Backend.service.ShoppingCartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class ShoppingCartController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/cart")
    public String cart(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            Customer customer = customerService.findByUsername(principal.getName());
            ShoppingCart shoppingCart = customer.getShoppingCart();
            if (shoppingCart == null) {
                model.addAttribute("check");
            }
            if (shoppingCart != null) {
                model.addAttribute("grandTotal", String.format("%.2f", shoppingCart.getTotalPrice()));
            }
            model.addAttribute("ShoppingCart", shoppingCart);
            model.addAttribute("title", "Carrinho");
            return "cart";
        }

    }

    @PostMapping("/add-to-cart")
    public String addItemToCart(@RequestParam("id") Long id,
                                @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
                                HttpServletRequest request,
                                Model model,
                                Principal principal,
                                HttpSession session) {

        ProductDto productDto = productService.getById(id);
        if (principal == null) {
            return "redirect:/login";
        } else {
            String username = principal.getName();
            ShoppingCart shoppingCart = shoppingCartService.addItemToCart(productDto, quantity, username);
            session.setAttribute("totalItems", shoppingCart.getTotalItems());
            model.addAttribute("shoppingCart", shoppingCart);
        }
        return "redirect:" + request.getHeader("Referer");
    }

    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=update")
    public String updateCart(@RequestParam("id") Long id,
                             @RequestParam("quantity") int quantity,
                             Model model,
                             Principal principal,
                             HttpSession session) {
        if (principal == null) {
            return "redirect:/login";
        }
        ProductDto productDto = productService.getById(id);
        String username = principal.getName();
        ShoppingCart shoppingCart = shoppingCartService.updateCart(productDto, quantity, username);
        model.addAttribute("shoppingCart", shoppingCart);
        session.setAttribute("totalItems", shoppingCart.getTotalItems());
        return "redirect:/cart";

    }

    @RequestMapping(value = "/update-cart", method = RequestMethod.POST, params = "action=delete")
    public String deleteItem(@RequestParam("id") Long id,
                             Model model,
                             Principal principal,
                             HttpSession session
    ) {
        if (principal == null) {
            return "redirect:/login";
        } else {
            ProductDto productDto = productService.getById(id);
            String username = principal.getName();
            ShoppingCart shoppingCart = shoppingCartService.removeItemFromCart(productDto, username);
            model.addAttribute("shoppingCart", shoppingCart);
            session.setAttribute("totalItems", shoppingCart.getTotalItems());
            return "redirect:/cart";
        }
    }
}