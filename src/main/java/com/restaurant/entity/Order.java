package com.restaurant.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "client_id")
    private Long clientId;
    @Column(name = "menu_id")
    private Long menuId;
    @Column(name = "amount")
    private int amount;
    @Column(name = "status")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private StatusOrder status;

//    public enum StatusOrder {ORDERED, ISSUED_FOR_PAYMENT, PAID}
    public enum StatusOrder {ordered, issued_for_payment, paid}

}
