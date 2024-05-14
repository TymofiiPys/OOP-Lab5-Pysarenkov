package com.restaurant.entity;

import jakarta.persistence.*;

import lombok.Data;


@Data
@Entity
@Table(name = "menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    /**
     * Represents whether the item is a meal or a drink, where
     * {@code true} means it is a meal, and {@code false} - a drink.
     */
    @Column(name = "meal_drink")
    private boolean mealOrDrink;
    @Column(name = "cost")
    private double cost;
}
