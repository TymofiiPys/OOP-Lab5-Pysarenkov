package com.restaurant.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "client_id")
    private int clientId;
    @Column(name = "time")
    private Timestamp time;
    @Column(name = "cost")
    private double cost;
}
