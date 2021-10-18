package com.geekbrains.webapp.dtos;

import com.geekbrains.webapp.model.Comment;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;


@Data
@NoArgsConstructor
public class CommentDto {
    @Nullable
    private Long id;
    private String comment;
    private String userName;
    @Nullable
    private Long userId;
    private Long productId;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.userName = comment.getUser().getUsername();
        this.userId = comment.getUser().getId();
        this.productId = comment.getProduct().getId();
    }
}
