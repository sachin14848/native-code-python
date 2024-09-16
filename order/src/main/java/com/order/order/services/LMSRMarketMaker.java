package com.order.order.services;

import com.order.order.entity.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
@Service
public class LMSRMarketMaker {

    private final RedisTemplate<String, OrderEntity> redisTemplate;
    private double[] shares;
    private double b;
    private double floorPrice;
    private final AtomicInteger orderCounter = new AtomicInteger(0);

    public void initializeMarketMaker(int outcomesCount, double b, double[] initialShares, double floorPrice) {
        this.shares = new double[outcomesCount];
        this.b = b;
        this.floorPrice = floorPrice;
        System.arraycopy(initialShares, 0, this.shares, 0, outcomesCount);
    }

    public double getPrice(int outcome) {
        double sumExp = Arrays.stream(this.shares)
                .map(q -> Math.exp(q / this.b))
                .sum();
        double rawPrice = Math.exp(this.shares[outcome] / this.b) / sumExp;
        return Math.max(rawPrice, floorPrice);
    }

    public OrderEntity placeOrder(int outcome, double numberOfShares) {
        double initialCost = this.b * Math.log(
                Arrays.stream(this.shares)
                        .map(q -> Math.exp(q / this.b))
                        .sum());

        this.shares[outcome] += numberOfShares;

        double finalCost = this.b * Math.log(
                Arrays.stream(this.shares)
                        .map(q -> Math.exp(q / this.b))
                        .sum());

        double cost = finalCost - initialCost;

        // Create and store the order in Redis
        int orderId = orderCounter.incrementAndGet();
        OrderEntity order = new OrderEntity(orderId, outcome, numberOfShares, cost, getPrice(0), getPrice(1));
        redisTemplate.opsForValue().set("order:" + orderId, order);
        return order;
    }

    public OrderEntity getOrder(int orderId) {
        return redisTemplate.opsForValue().get("order:" + orderId);
    }



    public List<OrderEntity> getAllOrders() {
        // Logic to retrieve all orders from Redis would go here
        // For example, you could use a Redis hash or scan for all keys that match a pattern
        return null; // Placeholder for now
    }
}
