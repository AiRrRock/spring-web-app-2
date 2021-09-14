package com.geekbrains.webapp.services;

import com.geekbrains.webapp.dtos.OrderItemDto;
import com.geekbrains.webapp.model.Order;
import com.geekbrains.webapp.model.Product;
import com.geekbrains.webapp.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductService productService;
    private final OrderRepository orderRepository;
    public void save(List<OrderItemDto> dtoList) {
        Order order = new Order();
        List<Product> products = dtoList.stream().map(i -> productService.findById(i.getProductId()).get()).collect(Collectors.toList());
        order.setProducts(products);
        order.setTotalPrice(dtoList.stream().map(OrderItemDto::getPrice).reduce(0, Integer::sum));
        orderRepository.save(order);
    }
}
