package com.geekbrains.webapp.controllers;

import com.geekbrains.webapp.services.CartService;
import com.geekbrains.webapp.utils.Cart;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.UUID;

import static com.geekbrains.webapp.services.CartService.CARD_PREFIX;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public String getCardId(Principal principal) {
        String cardId = CARD_PREFIX;
        if (principal == null || principal.getName() == null || principal.getName().isEmpty()) {
            cardId += UUID.randomUUID();
        } else {
            cardId += principal.getName();
        }
        return JSONObject.quote(cardId);
    }

    @GetMapping("{cartId}")
    public Cart getCartForCurrentUser(@PathVariable String cartId) {
        return cartService.getCartForCurrentUser(cartId);
    }

    @GetMapping("{cartId}/add/{productId}")
    public void addToCart(@PathVariable String cartId, @PathVariable Long productId) {
        cartService.addItem(cartId, productId);
    }

    @GetMapping("{cartId}/decrement/{productId}")
    public void decrementItem(@PathVariable String cartId, @PathVariable Long productId) {
        cartService.decrementItem(cartId, productId);
    }

    @GetMapping("{cartId}/remove/{productId}")
    public void removeItem(@PathVariable String cartId, @PathVariable Long productId) {
        cartService.removeItem(cartId, productId);
    }
}
