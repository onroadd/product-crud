package com.example.productcrud.controller;

import com.example.productcrud.model.Category;
import com.example.productcrud.model.Product;
import com.example.productcrud.model.User;
import com.example.productcrud.service.CategoryService;
import com.example.productcrud.service.CustomUserDetails;
import com.example.productcrud.service.ProductService;
import java.time.LocalDate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    // Helper method untuk mendapatkan User yang sedang login
    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && auth.getPrincipal() instanceof CustomUserDetails) {
            return ((CustomUserDetails) auth.getPrincipal()).getUser();
        }
        return null;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/products";
    }

    @GetMapping("/products")
    public String listProducts(Model model) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return "redirect:/auth/login";
        }
        model.addAttribute("products", productService.findAllByUser(currentUser));
        return "product/list";
    }

    @GetMapping("/products/{id}")
    public String detailProduct(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return "redirect:/auth/login";
        }

        return productService.findByIdAndUser(id, currentUser)
                .map(product -> {
                    model.addAttribute("product", product);
                    return "product/detail";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "Produk tidak ditemukan atau bukan milik Anda");
                    return "redirect:/products";
                });
    }

    @GetMapping("/products/new")
    public String showCreateForm(Model model) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return "redirect:/auth/login";
        }

        Product product = new Product();
        product.setCreatedAt(LocalDate.now());
        product.setCategory(new Category()); // initialize for binding
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.findAllByUser(currentUser));
        return "product/form";
    }

    @PostMapping("/products/save")
    public String saveProduct(@ModelAttribute Product product, Model model, RedirectAttributes redirectAttributes) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return "redirect:/auth/login";
        }

        try {
            // Validate and resolve category
            if (product.getCategory() == null || product.getCategory().getId() == null) {
                throw new IllegalArgumentException("Kategori harus dipilih");
            }
            
            // Verify category belongs to current user and fetch managed entity
            Category actualCategory = categoryService.findByIdAndUser(product.getCategory().getId(), currentUser)
                .orElseThrow(() -> new IllegalArgumentException("Kategori tidak ditemukan atau bukan milik Anda"));
            
            product.setCategory(actualCategory);
            product.setCreatedBy(currentUser.getUsername());
            product.setUpdatedBy(currentUser.getUsername());
            
            productService.save(product, currentUser);
            redirectAttributes.addFlashAttribute("successMessage", "Produk berhasil disimpan!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            // Return to form with existing product data and categories
            model.addAttribute("categories", categoryService.findAllByUser(currentUser));
            return "product/form";
        }

        return "redirect:/products";
    }

    @GetMapping("/products/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return "redirect:/auth/login";
        }

        return productService.findByIdAndUser(id, currentUser)
                .map(product -> {
                    model.addAttribute("product", product);
                    model.addAttribute("categories", categoryService.findAllByUser(currentUser));
                    return "product/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "Produk tidak ditemukan atau bukan milik Anda");
                    return "redirect:/products";
                });
    }

    @PostMapping("/products/{id}/delete")
    public String deleteProduct(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            return "redirect:/auth/login";
        }

        try {
            productService.deleteByIdAndUser(id, currentUser);
            redirectAttributes.addFlashAttribute("successMessage", "Produk berhasil dihapus");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/products";
    }
}
