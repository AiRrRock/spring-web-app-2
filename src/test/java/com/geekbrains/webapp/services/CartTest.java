package com.geekbrains.webapp.services;

import com.geekbrains.webapp.configs.RedisConfig;
import com.geekbrains.webapp.model.Product;
import com.geekbrains.webapp.utils.Cart;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@SpringBootTest(classes = {CartService.class, RedisConfig.class, ProductService.class})
public class CartTest {
    public static final String CARD_ID = "id1";
    public static final long PRODUCT_ID = 1L;
    @Autowired
    private CartService cartService;

    @MockBean
    private ProductService productService;

    @Spy
    private RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

    static ValueOperations<String, Object> carts;
    static Cart cart;
    static Product product;

    @BeforeAll
    private static void before() {
        product = new Product();
        product.setPrice(10);
        product.setId(PRODUCT_ID);

        cart = new Cart();
        cart.clear();
        carts = new ValueOperations<String, Object>() {
            @Override
            public void set(String s, Object o) {
            }

            @Override
            public void set(String s, Object o, long l, TimeUnit timeUnit) {
            }

            @Override
            public Boolean setIfAbsent(String s, Object o) {
                return null;
            }

            @Override
            public Boolean setIfAbsent(String s, Object o, long l, TimeUnit timeUnit) {
                return null;
            }

            @Override
            public Boolean setIfPresent(String s, Object o) {
                return null;
            }

            @Override
            public Boolean setIfPresent(String s, Object o, long l, TimeUnit timeUnit) {
                return null;
            }

            @Override
            public void multiSet(Map<? extends String, ?> map) {

            }

            @Override
            public Boolean multiSetIfAbsent(Map<? extends String, ?> map) {
                return null;
            }

            @Override
            public Object get(Object o) {
                return cart;
            }

            @Override
            public Object getAndSet(String s, Object o) {
                return null;
            }

            @Override
            public List<Object> multiGet(Collection<String> collection) {
                return null;
            }

            @Override
            public Long increment(String s) {
                return null;
            }

            @Override
            public Long increment(String s, long l) {
                return null;
            }

            @Override
            public Double increment(String s, double v) {
                return null;
            }

            @Override
            public Long decrement(String s) {
                return null;
            }

            @Override
            public Long decrement(String s, long l) {
                return null;
            }

            @Override
            public Integer append(String s, String s2) {
                return null;
            }

            @Override
            public String get(String s, long l, long l1) {
                return null;
            }

            @Override
            public void set(String s, Object o, long l) {

            }

            @Override
            public Long size(String s) {
                return null;
            }

            @Override
            public Boolean setBit(String s, long l, boolean b) {
                return null;
            }

            @Override
            public Boolean getBit(String s, long l) {
                return null;
            }

            @Override
            public List<Long> bitField(String s, BitFieldSubCommands bitFieldSubCommands) {
                return null;
            }

            @Override
            public RedisOperations<String, Object> getOperations() {
                return null;
            }
        };

    }

    @BeforeEach
    void cleanCart() {
        cartService.clearCart(CARD_ID);
    }

    @Test
    public void addToCart() {
        Mockito.doReturn(true).when(redisTemplate).hasKey(CARD_ID);
        Mockito.doReturn(carts).when(redisTemplate).opsForValue();
        Mockito.doReturn(Optional.of(product)).when(productService).findById(PRODUCT_ID);

        cartService.addItem(CARD_ID, PRODUCT_ID);
        cartService.addItem(CARD_ID, PRODUCT_ID);
        cartService.addItem(CARD_ID, PRODUCT_ID);
        cartService.addItem(CARD_ID, PRODUCT_ID);
        cartService.addItem(CARD_ID, PRODUCT_ID);
        Assertions.assertEquals(5, cartService.getCartForCurrentUser(CARD_ID).getItems().get(0).getQuantity());

    }

    @Test
    public void decrementItemCount() {
        Mockito.doReturn(true).when(redisTemplate).hasKey(CARD_ID);
        Mockito.doReturn(carts).when(redisTemplate).opsForValue();
        Mockito.doReturn(Optional.of(product)).when(productService).findById(PRODUCT_ID);

        cartService.addItem(CARD_ID, PRODUCT_ID);
        cartService.addItem(CARD_ID, PRODUCT_ID);
        cartService.addItem(CARD_ID, PRODUCT_ID);
        cartService.addItem(CARD_ID, PRODUCT_ID);
        cartService.addItem(CARD_ID, PRODUCT_ID);

        cartService.decrementItem(CARD_ID, PRODUCT_ID);
        cartService.decrementItem(CARD_ID, PRODUCT_ID);
        cartService.decrementItem(CARD_ID, PRODUCT_ID);
        Assertions.assertEquals(2, cartService.getCartForCurrentUser(CARD_ID).getItems().get(0).getQuantity());

    }

    @Test
    public void removeItem() {
        Mockito.doReturn(true).when(redisTemplate).hasKey(CARD_ID);
        Mockito.doReturn(carts).when(redisTemplate).opsForValue();
        Mockito.doReturn(Optional.of(product)).when(productService).findById(PRODUCT_ID);

        cartService.addItem(CARD_ID, PRODUCT_ID);
        cartService.removeItem(CARD_ID, PRODUCT_ID);
        Assertions.assertEquals(0, cartService.getCartForCurrentUser(CARD_ID).getItems().size());

    }

}
