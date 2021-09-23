package com.geekbrains.webapp.services;

import com.geekbrains.webapp.exceptions.ResourceNotFoundException;
import com.geekbrains.webapp.model.Product;
import com.geekbrains.webapp.utils.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductService productService;
    private final RedisTemplate<String, Object> redisTemplate;

    public Cart getCartForCurrentUser() {
        if (!redisTemplate.hasKey("cart")) {
            redisTemplate.opsForValue().set("cart", new Cart());
        }
        Cart cart = (Cart) redisTemplate.opsForValue().get("cart");
        return cart;
    }

    public void updateCart(Cart cart) {
        redisTemplate.opsForValue().set("cart", cart);
    }

    public void addItem(Long productId) {
        Cart cart = getCartForCurrentUser();
        if (cart.add(productId)) {
            updateCart(cart);
            return;
        }
        Product product = productService.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Невозможно добавить продукт в корзину, так как продукт с id: " + productId + " не существует"));
        cart.add(product);
        updateCart(cart);
    }

    public void removeItem(Long productId) {
        Cart cart = getCartForCurrentUser();
        cart.remove(productId);
        updateCart(cart);
    }

    public void decrementItem(Long productId) {
        Cart cart = getCartForCurrentUser();
        cart.decrement(productId);
        updateCart(cart);
    }

    public void clearCart() {
        Cart cart = getCartForCurrentUser();
        cart.clear();
        updateCart(cart);
    }

    public boolean isCartExists() {
        return redisTemplate.hasKey("cart");
    }
}
