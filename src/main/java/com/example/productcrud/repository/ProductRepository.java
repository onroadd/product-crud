package com.example.productcrud.repository;

import com.example.productcrud.model.Category;
import com.example.productcrud.model.Product;
import com.example.productcrud.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    // Find all products where the product's category belongs to the given user
    // This works because Category has a ManyToOne relationship with User
    List<Product> findAllByCategoryUser(User user);
    
    // Alternative using JPQL if needed
    // @Query("SELECT p FROM Product p WHERE p.category.user = :user")
    // List<Product> findAllByUser(@Param("user") User user);
}
