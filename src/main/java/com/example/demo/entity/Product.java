package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private float weight;
    private float price;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}

