package com.example.productcrud.repository;

import com.example.productcrud.model.Category;
import com.example.productcrud.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    // Find all categories for a specific user
    List<Category> findByUser(User user);
    
    // Count categories for a user
    long countByUser(User user);
    
    // Check if category name exists for user (for validation)
    boolean existsByNameAndUser(String name, User user);
}
