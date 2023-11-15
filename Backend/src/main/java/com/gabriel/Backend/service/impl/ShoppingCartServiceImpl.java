package com.gabriel.Backend.service.impl;

import com.gabriel.Backend.dto.ProductDto;
import com.gabriel.Backend.model.CartItem;
import com.gabriel.Backend.model.Customer;
import com.gabriel.Backend.model.Product;
import com.gabriel.Backend.model.ShoppingCart;
import com.gabriel.Backend.repository.CartItemRepository;
import com.gabriel.Backend.repository.ShoppingCartRepository;
import com.gabriel.Backend.service.CustomerService;
import com.gabriel.Backend.service.ShoppingCartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Set;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Override
    @Transactional
    public ShoppingCart addItemToCart(ProductDto productDto, int quantity, String username) {
        Customer customer = customerService.findByUsername(username);
        ShoppingCart shoppingCart = customer.getShoppingCart();

        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
        }

        Set<CartItem> cartItems = shoppingCart.getCartItems();
        CartItem cartItem = find(cartItems, productDto.getId());

        Product product = modelMapper.map(productDto, Product.class);
        double unitPrice = productDto.getCostPrice();
        int itemQuantity = (cartItem == null) ? quantity : cartItem.getQuantity() + quantity;

        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(shoppingCart);
            cartItems.add(cartItem);
        }

        cartItem.setQuantity(itemQuantity);
        cartItem.setUnitPrice(unitPrice);
        cartItemRepository.save(cartItem);

        shoppingCart.setCartItems(cartItems);

        double totalPrice = totalPrice(cartItems);
        int totalItem = totalItem(cartItems);

        shoppingCart.setTotalPrice(totalPrice);
        shoppingCart.setTotalItems(totalItem);
        shoppingCart.setCustomer(customer);

        return shoppingCartRepository.save(shoppingCart);
    }


    @Override
    @Transactional
    public ShoppingCart updateCart(ProductDto productDto, int quantity, String username) {
        Customer customer = customerService.findByUsername(username);
        ShoppingCart shoppingCart = customer.getShoppingCart();
        Set<CartItem> cartItems = shoppingCart.getCartItems();
        CartItem item = find(cartItems, productDto.getId());

        item.setQuantity(quantity);
        cartItemRepository.save(item);

        updateShoppingCart(shoppingCart, cartItems);

        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ShoppingCart removeItemFromCart(ProductDto productDto, String username) {
        Customer customer = customerService.findByUsername(username);
        ShoppingCart shoppingCart = customer.getShoppingCart();
        Set<CartItem> cartItems = shoppingCart.getCartItems();
        CartItem item = find(cartItems, productDto.getId());

        cartItems.remove(item);
        cartItemRepository.delete(item);

        updateShoppingCart(shoppingCart, cartItems);

        return shoppingCartRepository.save(shoppingCart);
    }

    @Override
    @Transactional
    public void deleteCartById(Long id) {
        ShoppingCart shoppingCart = shoppingCartRepository.getById(id);
        if(!ObjectUtils.isEmpty(shoppingCart) && !ObjectUtils.isEmpty(shoppingCart.getCartItems())){
            cartItemRepository.deleteAll(shoppingCart.getCartItems());
        }
        shoppingCart.getCartItems().clear();
        shoppingCart.setTotalPrice(0);
        shoppingCart.setTotalItems(0);
        shoppingCartRepository.save(shoppingCart);
    }



    //Methods
    private void updateShoppingCart(ShoppingCart shoppingCart, Set<CartItem> cartItems) {
        shoppingCart.setCartItems(cartItems);
        shoppingCart.setTotalItems(totalItem(cartItems));
        shoppingCart.setTotalPrice(totalPrice(cartItems));
    }

    private CartItem find(Set<CartItem> cartItems, long productId) {
        return cartItems.stream()
                .filter(item -> item.getProduct().getId() == productId)
                .findFirst()
                .orElse(null);
    }

    private int totalItem(Set<CartItem> cartItems) {
        return cartItems.stream().mapToInt(CartItem::getQuantity).sum();
    }

    private double totalPrice(Set<CartItem> cartItems) {
        return cartItems.stream()
                .mapToDouble(item -> item.getUnitPrice() * item.getQuantity())
                .sum();
    }
}