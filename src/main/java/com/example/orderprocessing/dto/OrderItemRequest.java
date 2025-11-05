package com.example.orderprocessing.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class OrderItemRequest {
    @NotBlank
    private String product;
    @Min(1)
    private Integer quantity;
    private Double price;

    // getters and setters
    public String getProduct() { return product; }
    public void setProduct(String product) { this.product = product; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}
