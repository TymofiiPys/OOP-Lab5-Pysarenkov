package com.restaurant.controller;

import com.restaurant.dto.PaymentCreateDTO;
import com.restaurant.dto.PaymentDisplayDTO;
import com.restaurant.entity.Client;
import com.restaurant.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<PaymentDisplayDTO>> getPayments(
            @RequestAttribute(name = "client") Client client
    ) {
        if (!client.isAdmin()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<PaymentDisplayDTO> payments = paymentService.getAllPayments();
        if (payments == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(payments);
    }

    @PostMapping
    public ResponseEntity<PaymentDisplayDTO> createPayment(
            @RequestBody PaymentCreateDTO payments,
            @RequestAttribute(name = "client") Client client
    ) {
        PaymentDisplayDTO createdPayment = paymentService.createPayment(payments, client.getId());
        if (createdPayment == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(createdPayment);
        }
    }
}
