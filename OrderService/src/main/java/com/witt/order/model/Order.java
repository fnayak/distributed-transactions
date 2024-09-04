package com.witt.order.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders")
@Setter
@Getter
public class Order {

    @Id
    private String id;
    private String userId;
    private String status;
    private double amount;
}
