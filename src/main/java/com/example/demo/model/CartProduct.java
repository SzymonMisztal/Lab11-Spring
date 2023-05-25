package com.example.demo.model;

import com.example.demo.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartProduct {
    Product product;
    int quantity;
}
