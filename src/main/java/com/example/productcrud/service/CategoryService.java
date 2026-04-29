package com.example.productcrud.service;

import com.example.productcrud.model.Category;
import com.example.productcrud.model.Product;
import com.example.productcrud.model.User;
import com.example.productcrud.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Get all categories for a specific user
     */
    public List<Category> findAllByUser(User user) {
        return categoryRepository.findByUser(user);
    }

    /**
     * Find category by ID and verify ownership
     */
    public Optional<Category> findByIdAndUser(Long id, User user) {
        return categoryRepository.findById(id)
                .filter(cat -> cat.getUser() != null && cat.getUser().getId().equals(user.getId()));
    }

    /**
     * Get category by ID (without user check - for internal use)
     */
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    /**
     * Save category (new or update) with user assignment
     */
    public Category save(Category category, User user) {
        // Set user association
        category.setUser(user);
        
        // Validate unique name per user
        if (category.getName() != null && category.getName().trim().length() > 0) {
            String normalizedName = category.getName().trim();
            boolean exists = categoryRepository.existsByNameAndUser(normalizedName, user);
            if (exists && (category.getId() == null)) {
                throw new IllegalArgumentException(
                    "Category dengan nama '" + normalizedName + "' sudah ada untuk user Anda"
                );
            }
        }
        
        return categoryRepository.save(category);
    }

    /**
     * Delete category by ID and verify ownership
     * Also checks if category is used in any products
     */
    public void deleteByIdAndUser(Long id, User user) {
        Optional<Category> categoryOpt = findByIdAndUser(id, user);
        
        if (categoryOpt.isEmpty()) {
            throw new IllegalArgumentException("Category tidak ditemukan atau bukan milik Anda");
        }
        
        Category category = categoryOpt.get();
        
        // Check if category is used in any products
        // Note: category.getProducts() may be lazy-loaded, so use count query if needed
        // For now, we'll check size of collection (may trigger lazy load)
        long productCount = category.getProducts() != null ? category.getProducts().size() : 0;
        if (productCount > 0) {
            throw new IllegalStateException(
                "Tidak dapat menghapus category yang masih digunakan oleh " + productCount + " produk"
            );
        }
        
        categoryRepository.delete(category);
    }
}
