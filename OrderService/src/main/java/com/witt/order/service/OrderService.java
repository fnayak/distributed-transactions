package com.witt.order.service;

import com.witt.order.dto.OrderRequest;
import com.witt.order.model.Order;
import com.witt.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public boolean prepareOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setUserId(orderRequest.getUserId());
        order.setAmount(orderRequest.getAmount());
        order.setStatus("PREPARED");
        orderRepository.save(order);
        return true;
    }

    public void commitOrder(OrderRequest orderRequest) {
        orderRepository.findById(orderRequest.getOrderId()).ifPresent(order -> {
            order.setStatus("COMMITTED");
            orderRepository.save(order);
        });
    }

    public void rollbackOrder(OrderRequest orderRequest) {
        orderRepository.findById(orderRequest.getOrderId()).ifPresent(order -> {
            order.setStatus("CANCELLED");
            orderRepository.save(order);
        });
    }
}
