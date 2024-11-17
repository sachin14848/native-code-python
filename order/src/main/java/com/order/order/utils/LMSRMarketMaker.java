package com.amservicescrm.test;

import java.util.Arrays;

public class LMSRMarketMaker {
    
    private double[] shares;
    private double b;
    double floorPrice;


    // Constructor with initial shares to set base prices
    public LMSRMarketMaker(int outcomesCount, double b, double[] initialShares, double floorPrice) {
        this.shares = new double[outcomesCount];
        this.b = b;
        this.floorPrice = floorPrice;
        // Initialize the shares to set the base prices
        System.arraycopy(initialShares, 0, this.shares, 0, outcomesCount);
    }

    public double getPrice(int outcome) {
        double sumExp = Arrays.stream(this.shares)
                .map(q -> Math.exp(q / this.b))
                .sum();
        double rawPrice = Math.exp(this.shares[outcome] / this.b) / sumExp;
        return Math.max(rawPrice, floorPrice);
    }

    public double buy(int outcome, double numberOfShares) {
        double initialCost = this.b * Math.log(
                Arrays.stream(this.shares)
                        .map(q -> Math.exp(q / this.b))
                        .sum());

        this.shares[outcome] += numberOfShares;

        double finalCost = this.b * Math.log(
                Arrays.stream(this.shares)
                        .map(q -> Math.exp(q / this.b))
                        .sum());

        return finalCost - initialCost;
    }

    public void updateLiquidityParameter(double newB) {
        System.out.println("##########@@@@@@@@@@@@@$$$$$$$$$$Updated liquidity parameter to 500. New No price: " + this.b);
        this.b = newB;
    }


    public static void main(String[] args) {
        double initialPrice = 10.0;
        double b = 100.0;
        double floorPrice = 0.05;


        // Calculate the initial shares required to achieve the initial price
        double initialShares = b * Math.log(initialPrice * 2);

        // Set initial shares for both outcomes
        double[] initialSharesArray = {initialShares, initialShares};
        LMSRMarketMaker marketMaker = new LMSRMarketMaker(2, b, initialSharesArray, floorPrice);

        // Initial prices
        System.out.println("Initial Yes price: " + marketMaker.getPrice(0) * 10);
        System.out.println("Initial No price: " + marketMaker.getPrice(1) * 10);
        for (int i = 0; i < 5; i = i + 1) {

//            if (i == 600) {
//                marketMaker.updateLiquidityParameter(500);
//                System.out.println("##########@@@@@@@@@@@@@$$$$$$$$$$Updated liquidity parameter to 500. New No price: " +  marketMaker.b);
//            }

//            double costToBuy;// Buying 1 Yes share each time
//            if (i > 2) {
//                costToBuy = marketMaker.buy(1, 5);
//                System.out.println("Iteration " + (i + 1) + ": Cost to buy 1 No share: " + costToBuy * 10);
//            } else {
//                costToBuy = marketMaker.buy(0, 5);
//                System.out.println("Iteration " + (i + 1) + ": Cost to buy 1 Yes share: " + costToBuy * 10);
//            }
            double costToBuy = marketMaker.buy(1, 5);
            System.out.println("Iteration " + (i + 1) + ": Cost to buy 1 No share: " + costToBuy * 10);
            System.out.println("New Yes price: " + marketMaker.getPrice(0) * 10);
            System.out.println("New No price: " + marketMaker.getPrice(1) * 10);
            double costToBuy = marketMaker.buy(0, 5);
            System.out.println("Iteration " + "No" + ": Cost to buy 1 No share: " + costToBuy * 10);
            System.out.println("New Yes price: " + marketMaker.getPrice(0) * 10);
            System.out.println("New No price: " + marketMaker.getPrice(1) * 10);
//        }
//        }

        }
        double costToBuy = marketMaker.buy(0, 5);
        System.out.println("Iteration " + "No" + ": Cost to buy 1 No share: " + costToBuy * 10);
        System.out.println("New Yes price: " + marketMaker.getPrice(0) * 10);
        System.out.println("New No price: " + marketMaker.getPrice(1) * 10);
    }
}