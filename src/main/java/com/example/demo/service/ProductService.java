package com.example.demo.service;

import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createProduct(Product product) {
        productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getById(long id) {
        return productRepository.findById(id).orElseThrow();
    }

    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    public void updateProduct(Product product) {
        Product savedProduct = getById(product.getId());
        BeanUtils.copyProperties(product, savedProduct);
        productRepository.save(savedProduct);
    }
}
