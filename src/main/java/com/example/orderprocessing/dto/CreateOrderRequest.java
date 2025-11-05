package com.example.orderprocessing.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

public class CreateOrderRequest {
    @NotBlank
    private String customer;

    @NotEmpty
    private List<OrderItemRequest> items;

    // getters and setters
    public String getCustomer() { return customer; }
    public void setCustomer(String customer) { this.customer = customer; }
    public List<OrderItemRequest> getItems() { return items; }
    public void setItems(List<OrderItemRequest> items) { this.items = items; }
}
