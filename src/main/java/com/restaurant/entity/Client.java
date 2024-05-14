package com.restaurant.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "email")
    private String email;
    @Column(name = "is_admin")
    private boolean isAdmin;
    @Column(name = "password")
    private String hash;
    @Column(name = "salt")
    private String salt;
}
