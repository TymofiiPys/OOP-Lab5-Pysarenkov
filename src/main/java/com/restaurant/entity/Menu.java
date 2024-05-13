package com.restaurant.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name = "menu")
public class Menu {
    @Id
    @Column(name = "id")
    private int id;
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
