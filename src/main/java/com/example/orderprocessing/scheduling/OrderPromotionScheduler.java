package com.example.orderprocessing.scheduling;

import com.example.orderprocessing.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderPromotionScheduler {
    private static final Logger logger = LoggerFactory.getLogger(OrderPromotionScheduler.class);
    private final OrderService orderService;

    public OrderPromotionScheduler(OrderService orderService) {
        this.orderService = orderService;
    }

    // Every 5 minutes
    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void promotePendingOrders() {
        logger.info("Scheduler: Promoting PENDING orders to PROCESSING");
        orderService.promotePendingToProcessing();
    }
}
