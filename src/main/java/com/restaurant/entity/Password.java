package com.restaurant.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Password {
    private String hash;
    private String salt;
}
