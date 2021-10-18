package com.geekbrains.webapp.controllers;

import com.geekbrains.webapp.dtos.CommentDto;
import com.geekbrains.webapp.dtos.ProductDto;
import com.geekbrains.webapp.exceptions.DataValidationException;
import com.geekbrains.webapp.exceptions.ResourceNotFoundException;
import com.geekbrains.webapp.model.Category;
import com.geekbrains.webapp.model.Comment;
import com.geekbrains.webapp.model.Product;
import com.geekbrains.webapp.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{productId}")
    public List<CommentDto> findByProductId(@PathVariable Long productId) {
        return commentService.findCommentsByProduct(productId);
    }

    @GetMapping("prepare/{productId}")
    public CommentDto findByProductId(Principal principal, @PathVariable Long productId) {
        CommentDto dto = new CommentDto();
        dto.setId(null);
        dto.setProductId(productId);
        dto.setUserName(principal.getName());
        return dto;
    }

    @PostMapping()
    public void addComment(Principal principal, @RequestBody CommentDto commentDto) {
        commentService.createComment(principal, commentDto);
    }

}
