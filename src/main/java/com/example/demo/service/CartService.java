package com.example.demo.service;

import com.example.demo.entity.Product;
import com.example.demo.model.Cart;
import com.example.demo.model.CartProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final ProductService productService;

    @Autowired
    public CartService(ProductService productService) {
        this.productService = productService;
    }

    public double getOverallPrice(Cart cart) {
        return cart.getProducts().stream()
                .mapToDouble(cartProduct -> cartProduct.getProduct().getPrice() * cartProduct.getQuantity())
                .sum();

    }

    public Cart addProduct(long productId, Cart cart) {
        Product product = productService.getById(productId);
        Optional<CartProduct> existing = cart.getProducts().stream()
                .filter(cartProduct -> cartProduct.getProduct().getId() == productId)
                .findAny();
        if (existing.isPresent()) {
            CartProduct cartProduct = existing.get();
            cartProduct.setQuantity(cartProduct.getQuantity()+1);
            List<CartProduct> products = cart.getProducts().stream()
                    .filter(cp -> cp.getProduct().getId() != productId)
                    .collect(Collectors.toList());
            products.add(cartProduct);

            cart.setProducts(products);
        } else {
            List<CartProduct> products = new ArrayList<>(cart.getProducts());
            products.add(new CartProduct(product, 1));
            cart.setProducts(products);
        }

        return cart;
    }

    public Cart removeProduct(long productId, Cart cart) {
        List<CartProduct> products = cart.getProducts().stream()
                .filter(cartProduct -> cartProduct.getProduct().getId() != productId)
                .toList();

        cart.setProducts(products);
        return cart;
    }

    public Cart decreaseProduct(long productId, Cart cart) {
        CartProduct product = cart.getProducts().stream()
                .filter(cartProduct -> cartProduct.getProduct().getId() == productId)
                .findAny()
                .orElseThrow();
        product.setQuantity(product.getQuantity() - 1);

        List<CartProduct> products = new ArrayList<>(cart.getProducts().stream()
                .filter(cartProduct -> cartProduct.getProduct().getId() != productId)
                .toList());

        if (product.getQuantity() > 0) {
            products.add(product);
        }

        cart.setProducts(products);
        return cart;
    }
}
