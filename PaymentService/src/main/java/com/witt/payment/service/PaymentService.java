package com.witt.payment.service;

import com.witt.payment.dto.OrderRequest;
import com.witt.payment.model.Payment;
import com.witt.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public boolean preparePayment(OrderRequest orderRequest) {
        Payment payment = new Payment();
        payment.setOrderId(orderRequest.getOrderId());
        payment.setAmount(orderRequest.getAmount());
        payment.setStatus("PREPARED");
        paymentRepository.save(payment);
        return true;
    }

    public void commitPayment(OrderRequest orderRequest) {
        paymentRepository.findById(orderRequest.getOrderId()).ifPresent(payment -> {
            payment.setStatus("COMMITTED");
            paymentRepository.save(payment);
        });
    }

    public void rollbackPayment(OrderRequest orderRequest) {
        paymentRepository.findById(orderRequest.getOrderId()).ifPresent(payment -> {
            payment.setStatus("CANCELLED");
            paymentRepository.save(payment);
        });
    }
}
