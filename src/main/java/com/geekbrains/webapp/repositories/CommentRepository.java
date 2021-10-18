package com.geekbrains.webapp.repositories;

import com.geekbrains.webapp.model.Comment;
import com.geekbrains.webapp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByProductId(Long product_id);

}
