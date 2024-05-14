package com.restaurant.dto;

import com.restaurant.entity.Order;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDisplayDTO {
    private Long id;
    private String clientName;
    private String menuItemName;
    private int amount;
    private Order.StatusOrder status;
    private double cost;
}
