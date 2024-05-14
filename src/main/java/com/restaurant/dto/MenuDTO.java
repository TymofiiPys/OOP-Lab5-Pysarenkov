package com.restaurant.dto;

import lombok.Data;

@Data
public class MenuDTO {
    private Long id;
    private String name;
    private boolean mealOrDrink;
    private double cost;
}
