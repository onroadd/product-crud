package com.example.productcrud.service;

import com.example.productcrud.model.Product;
import com.example.productcrud.model.User;
import com.example.productcrud.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Find all products for a specific user (through their categories)
     */
    public List<Product> findAllByUser(User user) {
        return productRepository.findAllByCategoryUser(user);
    }

    /**
     * Find product by ID and verify it belongs to user's categories
     */
    public Optional<Product> findByIdAndUser(Long id, User user) {
        return productRepository.findById(id)
                .filter(p -> p.getCategory() != null && 
                            p.getCategory().getUser() != null &&
                            p.getCategory().getUser().getId().equals(user.getId()));
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    /**
     * Save product - automatically sets user from category
     * Category must belong to the provided user
     */
    public Product save(Product product, User user) {
        // Validate that category belongs to user
        if (product.getCategory() != null) {
            if (!product.getCategory().getUser().getId().equals(user.getId())) {
                throw new IllegalArgumentException("Category bukan milik Anda");
            }
        }
        
        // Note: createdBy/updatedBy can be set from user.getUsername() if needed
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public void deleteByIdAndUser(Long id, User user) {
        findByIdAndUser(id, user).ifPresentOrElse(
            product -> productRepository.delete(product),
            () -> { throw new IllegalArgumentException("Product tidak ditemukan atau bukan milik Anda"); }
        );
    }
}
