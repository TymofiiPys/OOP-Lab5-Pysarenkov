package com.restaurant.service;

import com.restaurant.dto.PaymentCreateDTO;
import com.restaurant.dto.PaymentDisplayDTO;
import com.restaurant.entity.Client;
import com.restaurant.entity.Payment;
import com.restaurant.mapper.MenuMapper;
import com.restaurant.mapper.PaymentMapper;
import com.restaurant.repository.ClientRepository;
import com.restaurant.repository.MenuRepository;
import com.restaurant.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final ClientRepository clientRepository;
    private final PaymentMapper mapper = PaymentMapper.INSTANCE;
    public PaymentDisplayDTO createPayment(PaymentCreateDTO payment, Long clientId) {
        Payment paymentReduced = new Payment();
        paymentReduced.setClientId(clientId);
        paymentReduced.setTime(new Timestamp(new Date().getTime()));
        paymentReduced.setCost(payment.getCost());
        Payment createdPayment = paymentRepository.save(paymentReduced);
//        if(createdPayment == null) return null;
        return mapper.toPaymentDisplayDTO(createdPayment);
    }

    public List<PaymentDisplayDTO> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        List<PaymentDisplayDTO> paymentsToDisplay = payments.stream().map(mapper::toPaymentDisplayDTO).toList();
        for (var payment : paymentsToDisplay) {
            Optional<Client> client = clientRepository.findById(payment.getClientId());
            if(client.isPresent())
                payment.setClientName(client.get().getEmail());
            else
                payment.setClientName("");
        }
        return paymentsToDisplay;
    }
}
