package com.geekbrains.webapp.utils;

import com.geekbrains.webapp.dtos.OrderItemDto;
import com.geekbrains.webapp.model.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
public class Cart {
    private List<OrderItemDto> items;
    private int totalPrice;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public boolean add(Long productId) {
        for (OrderItemDto i : items) {
            if (i.getProductId().equals(productId)) {
                i.changeQuantity(1);
                recalculate();
                return true;
            }
        }
        return false;
    }

    public void add(OrderItemDto itemDto){
        items.add(itemDto);
        recalculate();
    }

    public void add(Product product) {
        items.add(new OrderItemDto(product));
        recalculate();
    }

    public void decrement(Long productId) {
        Iterator<OrderItemDto> iter = items.iterator();
        while (iter.hasNext()) {
            OrderItemDto i = iter.next();
            if (i.getProductId().equals(productId)) {
                i.changeQuantity(-1);
                if (i.getQuantity() <= 0) {
                    iter.remove();
                }
                recalculate();
                return;
            }
        }
    }

    public void remove(Long productId) {
        items.removeIf(i -> i.getProductId().equals(productId));
        recalculate();
    }

    public void clear() {
        items.clear();
        totalPrice = 0;
    }

    private void recalculate() {
        totalPrice = 0;
        for (OrderItemDto i : items) {
            totalPrice += i.getPrice();
        }
    }
}
