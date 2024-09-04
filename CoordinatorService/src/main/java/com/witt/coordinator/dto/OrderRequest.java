package com.witt.coordinator.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {

    private String orderId;
    private String userId;
    private double amount;

}
