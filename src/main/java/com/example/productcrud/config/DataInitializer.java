package com.example.productcrud.config;

import com.example.productcrud.model.Category;
import com.example.productcrud.model.User;
import com.example.productcrud.repository.CategoryRepository;
import com.example.productcrud.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UserRepository userRepository, 
                                       CategoryRepository categoryRepository,
                                       PasswordEncoder passwordEncoder) {
        return args -> {
            // Only create default admin if NO users exist at all
            if (userRepository.count() == 0) {
                // Create admin user (for testing only)
                User admin = new User();
                admin.setUsername("admin");
                admin.setEmail("admin@example.com");
                admin.setFullName("Admin User");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setEnabled(true);
                userRepository.save(admin);
                
                // Create default categories for admin
                String[] defaultCategories = {"Elektronik", "Buku", "Makanan", "Pakaian"};
                Arrays.stream(defaultCategories).forEach(name -> {
                    Category cat = new Category();
                    cat.setName(name);
                    cat.setDescription("Default category: " + name);
                    cat.setUser(admin);
                    categoryRepository.save(cat);
                });
                
                System.out.println("Default admin created: username=admin, password=admin123");
                System.out.println("Default categories created for admin");
            }
        };
    }
}
