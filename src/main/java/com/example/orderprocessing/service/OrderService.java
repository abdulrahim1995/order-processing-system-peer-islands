package com.example.orderprocessing.service;

import com.example.orderprocessing.entity.Order;
import com.example.orderprocessing.entity.OrderItem;
import com.example.orderprocessing.entity.OrderStatus;
import com.example.orderprocessing.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository repo;

    public OrderService(OrderRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public Order createOrder(Order order) {
        order.setStatus(OrderStatus.PENDING);
        return repo.save(order);
    }

    public Optional<Order> getOrder(Long id) {
        return repo.findById(id);
    }

    public List<Order> listOrders() {
        return repo.findAll();
    }

    public List<Order> listOrdersByStatus(OrderStatus status) {
        return repo.findByStatus(status);
    }

    @Transactional
    public Order cancelOrder(Long id) {
        Order o = repo.findById(id).orElseThrow(() -> new RuntimeException("Order not found: " + id));
        if (o.getStatus() != OrderStatus.PENDING) {
            throw new IllegalStateException("Only PENDING orders can be cancelled");
        }
        o.setStatus(OrderStatus.CANCELLED);
        return repo.save(o);
    }

    @Transactional
    public void promotePendingToProcessing() {
        List<Order> pending = repo.findByStatus(OrderStatus.PENDING);
        for (Order o : pending) {
            o.setStatus(OrderStatus.PROCESSING);
            repo.save(o);
        }
    }

    @Transactional
    public Order updateStatus(Long id, OrderStatus status) {
        Order o = repo.findById(id).orElseThrow(() -> new RuntimeException("Order not found: " + id));
        o.setStatus(status);
        return repo.save(o);
    }
}
