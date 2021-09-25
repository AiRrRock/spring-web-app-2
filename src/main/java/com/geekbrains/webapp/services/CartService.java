package com.geekbrains.webapp.services;

import com.geekbrains.webapp.dtos.OrderItemDto;
import com.geekbrains.webapp.exceptions.ResourceNotFoundException;
import com.geekbrains.webapp.model.Order;
import com.geekbrains.webapp.model.Product;
import com.geekbrains.webapp.utils.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.Principal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductService productService;
    private final RedisTemplate<String, Object> redisTemplate;
    public static final String CARD_PREFIX = "just_a_cart_";

    public Cart getCartForCurrentUser(String cardId) {
        if (!redisTemplate.hasKey(cardId)) {
            redisTemplate.opsForValue().set(cardId, new Cart());
        }
        Cart cart = (Cart) redisTemplate.opsForValue().get(cardId);
        return cart;
    }

    public String getCartName(String userName){
        return CARD_PREFIX+userName;
    }

    public void updateCart(String cardId, Cart cart) {
        redisTemplate.opsForValue().set(cardId, cart);
    }

    public void addItem(String cardId, Long productId) {
        Cart cart = getCartForCurrentUser(cardId);
        if (cart.add(productId)) {
            updateCart(cardId, cart);
            return;
        }
        Product product = productService.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Невозможно добавить продукт в корзину, так как продукт с id: " + productId + " не существует"));
        cart.add(product);
        updateCart(cardId, cart);
    }

    public void removeItem(String cardId, Long productId) {
        Cart cart = getCartForCurrentUser(cardId);
        cart.remove(productId);
        updateCart(cardId, cart);
    }

    public void decrementItem(String cardId, Long productId) {
        Cart cart = getCartForCurrentUser(cardId);
        cart.decrement(productId);
        updateCart(cardId, cart);
    }

    public void clearCart(String cardId) {
        Cart cart = getCartForCurrentUser(cardId);
        cart.clear();
        updateCart(cardId, cart);
    }

    public void merge(String cardId, String tempCardId){
        Cart userCart = getCartForCurrentUser(cardId);
        Cart tempCard = getCartForCurrentUser(tempCardId);
        for (OrderItemDto item: tempCard.getItems()){
            userCart.add(item);
        }
        updateCart(cardId, userCart);

    }

    public boolean isCartExists(String cardId) {
        return redisTemplate.hasKey(cardId);
    }
}
