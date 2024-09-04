package com.witt.payment.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "payments")
@Setter
@Getter
public class Payment {

    @Id
    private String id;
    private String orderId;
    private String status;
    private double amount;
}
