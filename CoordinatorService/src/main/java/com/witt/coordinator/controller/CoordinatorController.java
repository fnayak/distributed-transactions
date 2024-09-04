package com.witt.coordinator.controller;

import com.witt.coordinator.dto.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import static com.witt.coordinator.constant.CoordinatorConstant.ORDER_SERVICE;
import static com.witt.coordinator.constant.CoordinatorConstant.PAYMENT_SERVICE;

@RestController
@RequestMapping("/coordinator")
public class CoordinatorController {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/process")
    public ResponseEntity<String> processTransaction(@RequestBody OrderRequest orderRequest) {
        try {
            boolean orderPrepared = prepareOrder(orderRequest);
            boolean paymentPrepared = preparePayment(orderRequest);
            if (orderPrepared && paymentPrepared) {
                commitOrder(orderRequest);
                commitPayment(orderRequest);
                return new ResponseEntity<>("Transaction processed successfully", HttpStatus.OK);
            } else {
                rollbackOrder(orderRequest);
                rollbackPayment(orderRequest);
                return new ResponseEntity<>("Transaction failed and rolled back", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        catch (Exception e) {
            rollbackOrder(orderRequest);
            rollbackPayment(orderRequest);
            return new ResponseEntity<>("Transaction failed with error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean preparePayment(OrderRequest orderRequest) {
        ResponseEntity<Boolean> response = restTemplate.postForEntity(PAYMENT_SERVICE+"/payments/prepare", orderRequest, Boolean.class);
        return response.getBody();
    }

    private boolean prepareOrder(OrderRequest orderRequest) {
        ResponseEntity<Boolean> response = restTemplate.postForEntity(ORDER_SERVICE+"/orders/prepare", orderRequest, Boolean.class);
        return response.getBody();
    }

    private void commitOrder(OrderRequest orderRequest) {
        restTemplate.postForEntity(ORDER_SERVICE+"/orders/commit", orderRequest, Void.class);
    }

    private void commitPayment(OrderRequest orderRequest) {
        restTemplate.postForEntity(PAYMENT_SERVICE+"/payments/commit", orderRequest, Void.class);
    }

    private void rollbackPayment(OrderRequest orderRequest) {
        restTemplate.postForEntity(PAYMENT_SERVICE+"/payments/rollback", orderRequest, Void.class);
    }

    private void rollbackOrder(OrderRequest orderRequest) {
        restTemplate.postForEntity(ORDER_SERVICE+"/orders/rollback", orderRequest, Void.class);
    }
}
