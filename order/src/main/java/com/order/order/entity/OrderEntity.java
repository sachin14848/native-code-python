package com.order.order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity implements Serializable {

    private int id;
    private int outcome;
    private double numberOfShares;
    private double cost;
    private double priceOfYes;
    private double priceOfNo;


}
