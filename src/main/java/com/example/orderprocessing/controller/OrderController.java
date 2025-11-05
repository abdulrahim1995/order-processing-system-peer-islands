package com.example.orderprocessing.controller;

import com.example.orderprocessing.dto.CreateOrderRequest;
import com.example.orderprocessing.dto.OrderItemRequest;
import com.example.orderprocessing.entity.Order;
import com.example.orderprocessing.entity.OrderItem;
import com.example.orderprocessing.entity.OrderStatus;
import com.example.orderprocessing.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Order> createOrder(@Valid @RequestBody CreateOrderRequest req) {
        Order order = new Order();
        order.setCustomer(req.getCustomer());
        List<OrderItem> items = req.getItems().stream().map(i -> {
            OrderItem it = new OrderItem();
            it.setProduct(i.getProduct());
            it.setQuantity(i.getQuantity());
            it.setPrice(i.getPrice());
            return it;
        }).collect(Collectors.toList());
        order.setItems(items);
        Order saved = service.createOrder(order);
        return ResponseEntity.created(URI.create("/api/orders/" + saved.getId())).body(saved);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        return service.getOrder(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public List<Order> listOrders(@RequestParam(value = "status", required = false) OrderStatus status) {
        if (status != null) {
            return service.listOrdersByStatus(status);
        }
        return service.listOrders();
    }

    @PostMapping("/{id}/cancel")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Order> cancelOrder(@PathVariable Long id) {
        try {
            Order cancelled = service.cancelOrder(id);
            return ResponseEntity.ok(cancelled);
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(409).build();
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Order> updateStatus(@PathVariable Long id, @RequestParam OrderStatus status) {
        try {
            Order updated = service.updateStatus(id, status);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
