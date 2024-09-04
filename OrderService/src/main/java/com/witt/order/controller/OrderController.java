package com.witt.order.controller;

import com.witt.order.dto.OrderRequest;
import com.witt.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/prepare")
    public ResponseEntity<Boolean> prepareOrder(@RequestBody OrderRequest orderRequest) {
        return new ResponseEntity<>(orderService.prepareOrder(orderRequest), HttpStatus.OK);
    }

    @PostMapping("/commit")
    public ResponseEntity<Void> commitOrder(@RequestBody OrderRequest orderRequest) {
        orderService.commitOrder(orderRequest);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
    @PostMapping("/rollback")
    public ResponseEntity<Void> rollbackOrder(@RequestBody OrderRequest orderRequest) {
        orderService.rollbackOrder(orderRequest);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
