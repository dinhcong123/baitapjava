    package com.example.demo.Controller;

    import com.example.demo.models.Product;
    import com.example.demo.services.CategoryService;
    import com.example.demo.services.ProductService;
    import jakarta.validation.Valid;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.core.io.ClassPathResource;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.validation.BindingResult;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;

    import java.io.File;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    import java.nio.file.StandardCopyOption;
    import java.util.List;
    import java.util.UUID;

    @Controller
    @RequestMapping("/products")
    public class ProductController {
        @Autowired
        private ProductService productService;
        @Autowired
        private CategoryService categoryService; // Đảm bảo bạn đã inject

    // Display a list of all products
        @GetMapping("")
        public String showProductList(Model model) {
            model.addAttribute("products", productService.getAllProducts());
            return "/products/product-list";
        }
        // For adding a new product
        @GetMapping("/add")
        public String addProduct(Model model) {
            model.addAttribute("product", new Product());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "/products/add-product";
        }
        // Process the form for adding a new product
        @PostMapping("/add")
        public String addProduct(@Valid Product product, @RequestParam MultipartFile imageProduct, BindingResult result, Model model) {
            if (result.hasErrors()) {
                model.addAttribute("product", product);
                return "/products/add-product";
            }
            productService.updateImage(product,imageProduct);
            productService.addProduct(product);
            return "redirect:/products";
        }
        // For editing a product
        @GetMapping("/edit/{id}")
        public String showEditForm(@PathVariable Long id, Model model) {
            Product product = productService.getProductById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
            model.addAttribute("product", product);
            model.addAttribute("categories", categoryService.getAllCategories());
            return "/products/update-product";
        }
        // Process the form for updating a product
        @PostMapping("/edit/{id}")
        public String updateProduct(@PathVariable Long id,  @Valid Product product, @RequestParam MultipartFile imageProduct, BindingResult result, Model model) {
            if (result.hasErrors()) {
                product.setId(id);
                return "/products/update-product";
            }
            productService.updateImage(product,imageProduct);
            productService.updateProduct(product);
            return "redirect:/products";
        }
        // Handle request to delete a product
        @GetMapping("/delete/{id}")
        public String deleteProduct(@PathVariable Long id) {
            productService.deleteProductById(id);
            return "redirect:/products";
        }

        @GetMapping("/search")
        public String search(@RequestParam String keyword, Model model) {
            model.addAttribute("listproduct", productService.search(keyword));
            return "products/index";
        }
    }
