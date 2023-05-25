package com.example.demo.controller;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;
import org.hibernate.TransientPropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/admin/products")
    public String getAdminProductsView(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/user/products")
    public String getUserProductsView(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products_user";
    }

    @GetMapping("/admin/products/add")
    public String getAddProductView(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "add_product";
    }

    @GetMapping("/admin/products/edit/{id}")
    public String getEditProductView(Model model, @PathVariable long id) {
        Product product = productService.getById(id);
        model.addAttribute("product", product);
        return "edit_product";
    }

    @GetMapping("/admin/products/remove/{id}")
    public String getRemoveProductView(@PathVariable long id) {
        productService.deleteProduct(id);

        return "redirect:/admin/products";
    }

    @GetMapping("/admin/products/{id}")
    public String getDetailsProductView(Model model, @PathVariable long id) {
        Product product = productService.getById(id);
        model.addAttribute("product", product);

        return "details_product";
    }

    @PostMapping("/admin/products/edit")
    public String update(@ModelAttribute Product product) {
        productService.updateProduct(product);
        return "redirect:/admin/products";
    }

    @PostMapping("/admin/products")
    public String add(@ModelAttribute Product product) {
        productService.createProduct(product);
        return "redirect:/admin/products";
    }

}
