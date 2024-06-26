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
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final CustomerService customerService;
    private final ModelMapper modelMapper;
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    @Transactional
    public ShoppingCart addItemToCart(ProductDto productDto, int quantity, String username) {
        Customer customer = customerService.findByUsername(username);
        ShoppingCart shoppingCart = customer.getShoppingCart();

        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
        }

        Set<CartItem> cartItems = shoppingCart.getCartItems();
        CartItem cartItem = findCartItem(cartItems, productDto.getId());
        Product product = transfer(productDto);
        BigDecimal unitPrice = productDto.getCostPrice();

        int itemQuantity = 0;
        if (cartItems == null) {
            cartItems = new HashSet<>();
            if (cartItem == null) {
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setCart(shoppingCart);
                cartItem.setQuantity(quantity);
                cartItem.setUnitPrice(unitPrice);
                cartItem.setCart(shoppingCart);
                cartItems.add(cartItem);
                cartItemRepository.save(cartItem);
            } else {
                itemQuantity = cartItem.getQuantity() + quantity;
                cartItem.setQuantity(itemQuantity);
                cartItemRepository.save(cartItem);
            }
        } else {
            if (cartItem == null) {
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setCart(shoppingCart);
                cartItem.setQuantity(quantity);
                cartItem.setUnitPrice(unitPrice);
                cartItem.setCart(shoppingCart);
                cartItems.add(cartItem);
                cartItemRepository.save(cartItem);
            } else {
                itemQuantity = cartItem.getQuantity() + quantity;
                cartItem.setQuantity(itemQuantity);
                cartItemRepository.save(cartItem);
            }
        }
        shoppingCart.setCartItems(cartItems);

        BigDecimal totalPrice = totalPrice(shoppingCart.getCartItems());
        int totalItem = totalItem(shoppingCart.getCartItems());

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
        CartItem cartItem = findCartItem(cartItems, productDto.getId());

        if (cartItem != null) {
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
            updateShoppingCart(shoppingCart, cartItems);
            return shoppingCartRepository.save(shoppingCart);
        }

        throw new IllegalArgumentException("Item do carrinho não encontrado");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ShoppingCart removeItemFromCart(ProductDto productDto, String username) {
        Customer customer = customerService.findByUsername(username);
        ShoppingCart shoppingCart = customer.getShoppingCart();
        Set<CartItem> cartItems = shoppingCart.getCartItems();
        CartItem cartItem = findCartItem(cartItems, productDto.getId());

        if (cartItem != null) {
            cartItems.remove(cartItem);
            cartItemRepository.delete(cartItem);
            updateShoppingCart(shoppingCart, cartItems);
            return shoppingCartRepository.save(shoppingCart);
        }

        throw new IllegalArgumentException("Item do carrinho não encontrado");
    }

    @Override
    @Transactional
    public void deleteCartById(Long id) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Carrinho de compras não encontrado"));

        if (!ObjectUtils.isEmpty(shoppingCart.getCartItems())) {
            cartItemRepository.deleteAll(shoppingCart.getCartItems());
        }

        shoppingCart.getCartItems().clear();
        shoppingCart.setTotalPrice(BigDecimal.ZERO);
        shoppingCart.setTotalItems(0);
        shoppingCartRepository.save(shoppingCart);
    }

    private CartItem findCartItem(Set<CartItem> cartItems, long productId) {
        return cartItems.stream()
                .filter(item -> item.getProduct().getId() == productId)
                .findFirst()
                .orElse(null);
    }

    private CartItem createCartItem(ShoppingCart shoppingCart, ProductDto productDto, int quantity) {
        Product product = modelMapper.map(productDto, Product.class);
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setCart(shoppingCart);
        cartItem.setQuantity(quantity);
        cartItem.setUnitPrice(productDto.getCostPrice());
        cartItemRepository.save(cartItem);
        return cartItem;
    }

    private void updateShoppingCart(ShoppingCart shoppingCart, Set<CartItem> cartItems) {
        shoppingCart.setCartItems(cartItems);
        shoppingCart.setTotalItems(totalItem(cartItems));
        shoppingCart.setTotalPrice(totalPrice(cartItems));
    }

    private int totalItem(Set<CartItem> cartItems) {
        return cartItems.stream().mapToInt(CartItem::getQuantity).sum();
    }

    private BigDecimal totalPrice(Set<CartItem> cartItems) {
        return cartItems.stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    private Product transfer(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setCurrentQuantity(productDto.getCurrentQuantity());
        product.setCostPrice(productDto.getCostPrice());
        product.setSalePrice(productDto.getSalePrice());
        product.setDescription(productDto.getDescription());
        product.setImage(productDto.getImage());
        product.set_activated(productDto.is_activated());
        product.set_deleted(productDto.is_deleted());
        product.setCategory(productDto.getCategory());
        return product;
    }
}
