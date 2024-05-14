package com.restaurant.controller;

import com.restaurant.dto.PaymentCreateDTO;
import com.restaurant.dto.PaymentDisplayDTO;
import com.restaurant.service.PaymentService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<PaymentDisplayDTO>> getPayments() {
//        Client client = objectMapper.readValue(
//                req.getAttribute("client").toString(),
//                Client.class
//        );
//        if(!client.isAdmin()) {
//            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
//        }
        List<PaymentDisplayDTO> payments = paymentService.getAllPayments();
        if (payments == null)
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(payments);
    }

    @PostMapping
    public ResponseEntity<PaymentDisplayDTO> createPayment(@RequestBody List<PaymentCreateDTO> payments) {
        PaymentDisplayDTO createdPayment = paymentService.createPayment(payments);
        if(createdPayment == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(createdPayment);
        }
    }
}
