package com.order.order.controller;

import com.order.order.entity.OrderEntity;
import com.order.order.services.LMSRMarketMaker;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/market")
@RequiredArgsConstructor
public class OrderController {

    private LMSRMarketMaker mMaker;
    

    @Autowired
    public OrderController(LMSRMarketMaker marketMaker) {
        this.mMaker = marketMaker;
        // Initialize the market maker with two outcomes, a b value, and initial shares
        double initialPrice = 10.0;
        double b = 10.0;
        double floorPrice = 0.05;
        double initialShares = b * Math.log(initialPrice * 2);
        double[] initialSharesArray = {initialShares, initialShares};
        marketMaker.initializeMarketMaker(2, b, initialSharesArray, floorPrice);
    }

    @PostMapping("/order")
    public OrderEntity placeOrder(@RequestParam int outcome, @RequestParam double shares) {
        return mMaker.placeOrder(outcome, shares);
    }

    @GetMapping("/order/{id}")
    public OrderEntity getOrder(@PathVariable int id) {
        return mMaker.getOrder(id);
    }

    @GetMapping("/price/{outcome}")
    public double getPrice(@PathVariable int outcome) {
        return mMaker.getPrice(outcome);
    }

    @GetMapping("/orders")
    public List<OrderEntity> getAllOrders() {
        return mMaker.getAllOrders();
    }

}
