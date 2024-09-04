package com.witt.payment.controller;

import com.witt.payment.dto.OrderRequest;
import com.witt.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/prepare")
    public ResponseEntity<Boolean> preparePayment(@RequestBody OrderRequest orderRequest) {
        boolean prepared = paymentService.preparePayment(orderRequest);
        return new ResponseEntity<>(prepared, HttpStatus.OK);
    }

    @PostMapping("/commit")
    public ResponseEntity<Void> commitPayment(@RequestBody OrderRequest orderRequest) {
        paymentService.commitPayment(orderRequest);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("/rollback")
    public ResponseEntity<Void> rollbackPayment(@RequestBody OrderRequest orderRequest) {
        paymentService.rollbackPayment(orderRequest);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
