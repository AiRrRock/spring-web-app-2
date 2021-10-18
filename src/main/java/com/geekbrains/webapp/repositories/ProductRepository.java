package com.geekbrains.webapp.repositories;

import com.geekbrains.webapp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    String GET_ALL_PRODUCTS_FOR_USER = "select distinct(oi.product_id) from orders as o join order_items oi on o.id=oi.order_id join users u on o.user_id=u.id where u.username =:username";

    Optional<Product> findByTitle(String title);

    @Query(value = GET_ALL_PRODUCTS_FOR_USER, nativeQuery = true)
    List<Long> findAllProductIdsForUser(@Param("username") final String userName);
}
