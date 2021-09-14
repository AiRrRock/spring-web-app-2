package com.geekbrains.webapp.controllers;

import com.geekbrains.webapp.dtos.OrderDetailsDto;
import com.geekbrains.webapp.model.Order;
import com.geekbrains.webapp.services.CartService;
import com.geekbrains.webapp.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final CartService cartService;
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestBody OrderDetailsDto orderDetailsDto) {

        orderService.save(cartService.getItems());
        cartService.clearCart();

    }
}
