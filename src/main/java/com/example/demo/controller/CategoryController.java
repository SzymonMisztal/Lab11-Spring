package com.example.demo.controller;

import com.example.demo.entity.Category;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/admin/categories")
    public String getCategoriesView(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "categories";
    }

    @GetMapping("/admin/categories/add")
    public String getAddCategoryView(Model model) {
        Category category = new Category();
        model.addAttribute("category", category);
        return "add_category";
    }

    @GetMapping("/admin/categories/edit/{id}")
    public String getEditCategoryView(Model model, @PathVariable long id) {
        Category category = categoryService.getById(id);
        model.addAttribute("category", category);
        return "edit_category";
    }

    @GetMapping("/admin/categories/remove/{id}")
    public String getRemoveCategoryView(@PathVariable long id) {
        categoryService.deleteCategory(id);

        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/{id}")
    public String getDetailsCategoryView(Model model, @PathVariable long id) {
        Category category = categoryService.getById(id);
        model.addAttribute("category", category);

        return "details_category";
    }

    @PostMapping("/admin/categories/edit")
    public String update(@ModelAttribute Category category) {
        categoryService.updateCategory(category);
        return "redirect:/admin/categories";
    }

    @PostMapping("/admin/categories")
    public String add(@ModelAttribute Category category) {
        categoryService.createCategory(category);
        return "redirect:/admin/categories";
    }

}
