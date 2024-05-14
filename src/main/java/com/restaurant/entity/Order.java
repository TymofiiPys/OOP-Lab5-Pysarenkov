package com.restaurant.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "client_id")
    private Long clientId;
    @Column(name = "menu_id")
    private Long menuId;
    @Column(name = "amount")
    private int amount;
    @Column(name = "status")
    private StatusOrder status;

    public enum StatusOrder {ORDERED, ISSUED_FOR_PAYMENT, PAID}
}
