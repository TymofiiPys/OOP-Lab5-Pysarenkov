package com.restaurant.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PaymentDisplayDTO {
    private Long clientId;
    private String clientName;
    private Timestamp time;
    private double cost;
}
