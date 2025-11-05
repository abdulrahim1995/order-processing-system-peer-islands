package com.example.orderprocessing.service;

import com.example.orderprocessing.entity.Order;
import com.example.orderprocessing.entity.OrderStatus;
import com.example.orderprocessing.repository.OrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class OrderServiceTest {

    private OrderRepository repo;
    private OrderService service;

    @Before
    public void setup() {
        repo = mock(OrderRepository.class);
        service = new OrderService(repo);
    }

    @Test
    public void testCreateOrder_setsPendingAndSaves() {
        Order o = new Order();
        o.setCustomer("John");
        when(repo.save(any(Order.class))).thenReturn(o);

        Order saved = service.createOrder(o);

        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);
        verify(repo).save(captor.capture());
        assertEquals(OrderStatus.PENDING, captor.getValue().getStatus());
        assertEquals("John", saved.getCustomer());
    }

    @Test
    public void testCancelOrder_onlyPending() {
        Order o = new Order();
        o.setId(1L);
        o.setStatus(OrderStatus.PENDING);
        when(repo.findById(1L)).thenReturn(Optional.of(o));
        when(repo.save(any(Order.class))).thenReturn(o);

        Order cancelled = service.cancelOrder(1L);
        assertEquals(OrderStatus.CANCELLED, cancelled.getStatus());
    }

    @Test(expected = IllegalStateException.class)
    public void testCancelOrder_nonPending_throws() {
        Order o = new Order();
        o.setId(2L);
        o.setStatus(OrderStatus.PROCESSING);
        when(repo.findById(2L)).thenReturn(Optional.of(o));

        service.cancelOrder(2L);
    }
}
