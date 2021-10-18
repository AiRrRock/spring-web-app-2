package com.geekbrains.webapp.services;

import com.geekbrains.webapp.dtos.CommentDto;
import com.geekbrains.webapp.dtos.OrderItemDto;
import com.geekbrains.webapp.exceptions.ResourceNotFoundException;
import com.geekbrains.webapp.model.*;
import com.geekbrains.webapp.repositories.CommentRepository;
import com.geekbrains.webapp.repositories.OrderRepository;
import com.geekbrains.webapp.utils.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ProductService productService;


    public List<CommentDto> findCommentsByProduct(Long id) {
        return commentRepository.findAllByProductId(id).stream().map(CommentDto::new).collect(Collectors.toList());
    }

    @Transactional
    public void createComment(Principal principal, CommentDto commentDto) {
        if (productService.hasBoughtProduct(commentDto.getUserName(), commentDto.getProductId())) {
            User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new ResourceNotFoundException("Не удалось найти пользователя"));
            Product product = productService.findById(commentDto.getProductId()).orElseThrow(() -> new ResourceNotFoundException("Не удалось найти продукт"));
            Comment comment = new Comment();
            comment.setComment(commentDto.getComment());
            comment.setUser(user);
            comment.setProduct(product);
            commentRepository.save(comment);
        }
    }


}
