package com.example.productcrud.config;

import com.example.productcrud.model.Category;
import com.example.productcrud.model.Product;
import com.example.productcrud.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initProducts(ProductRepository productRepository) {
        return args -> {
            if (productRepository.count() == 0) {
                
                // Create sample products
                Product product1 = new Product();
                product1.setName("Laptop Asus VivoBook 15");
                product1.setDescription("Laptop dengan processor Intel Core i5, RAM 8GB, SSD 512GB");
                product1.setPrice(7500000);
                product1.setStock(10);
                product1.setCategory(Category.ELEKTRONIK);
                product1.setActive(true);
                product1.setCreatedAt(LocalDate.now());
                product1.setCreatedBy("system");
                product1.setUpdatedBy("system");
                productRepository.save(product1);

                Product product2 = new Product();
                product2.setName("Smartphone Samsung Galaxy A54");
                product2.setDescription("Smartphone dengan layar Super AMOLED 6.4 inch, kamera 50MP");
                product2.setPrice(4999000);
                product2.setStock(25);
                product2.setCategory(Category.ELEKTRONIK);
                product2.setActive(true);
                product2.setCreatedAt(LocalDate.now());
                product2.setCreatedBy("system");
                product2.setUpdatedBy("system");
                productRepository.save(product2);

                Product product3 = new Product();
                product3.setName("Sepatu Running Nike Air Max");
                product3.setDescription("Sepatu running dengan teknologi Air Max cushioning");
                product3.setPrice(1899000);
                product3.setStock(50);
                product3.setCategory(Category.PAKAIAN);
                product3.setActive(true);
                product3.setCreatedAt(LocalDate.now());
                product3.setCreatedBy("system");
                product3.setUpdatedBy("system");
                productRepository.save(product3);

                Product product4 = new Product();
                product4.setName("Buku Pemrograman Java");
                product4.setDescription("Buku panduan belajar Java dari dasar hingga mahir");
                product4.setPrice(125000);
                product4.setStock(100);
                product4.setCategory(Category.BUKU);
                product4.setActive(true);
                product4.setCreatedAt(LocalDate.now());
                product4.setCreatedBy("system");
                product4.setUpdatedBy("system");
                productRepository.save(product4);

                Product product5 = new Product();
                product5.setName("Meja Kerja Kayu Jati");
                product5.setDescription("Meja kerja dari kayu jati kualitas premium");
                product5.setPrice(3500000);
                product5.setStock(5);
                product5.setCategory(Category.ELEKTRONIK);
                product5.setActive(true);
                product5.setCreatedAt(LocalDate.now());
                product5.setCreatedBy("system");
                product5.setUpdatedBy("system");
                productRepository.save(product5);

                Product product6 = new Product();
                product6.setName("Kopi Robusta Premium 1Kg");
                product6.setDescription("Biji kopi robusta grade premium dari highlands");
                product6.setPrice(95000);
                product6.setStock(200);
                product6.setCategory(Category.MAKANAN);
                product6.setActive(true);
                product6.setCreatedAt(LocalDate.now());
                product6.setCreatedBy("system");
                product6.setUpdatedBy("system");
                productRepository.save(product6);

                Product product7 = new Product();
                product7.setName("Keyboard Mechanical RGB");
                product7.setDescription("Keyboard mechanical dengan switch blue dan lighting RGB");
                product7.setPrice(650000);
                product7.setStock(30);
                product7.setCategory(Category.ELEKTRONIK);
                product7.setActive(true);
                product7.setCreatedAt(LocalDate.now());
                product7.setCreatedBy("system");
                product7.setUpdatedBy("system");
                productRepository.save(product7);

                Product product8 = new Product();
                product8.setName("Tas Ransel Canon");
                product8.setDescription("Tas ransel untuk kamera dengan kapasitas 15 liter");
                product8.setPrice(450000);
                product8.setStock(20);
                product8.setCategory(Category.PAKAIAN);
                product8.setActive(true);
                product8.setCreatedAt(LocalDate.now());
                product8.setCreatedBy("system");
                product8.setUpdatedBy("system");
                productRepository.save(product8);

                System.out.println("=== Sample products initialized successfully ===");
            }
        };
    }
}
