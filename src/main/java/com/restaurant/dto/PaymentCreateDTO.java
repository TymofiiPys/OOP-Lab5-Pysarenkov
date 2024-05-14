package com.restaurant.dto;

import lombok.Data;

@Data
public class PaymentCreateDTO {
    private Long clientId;
    private double cost;
}
