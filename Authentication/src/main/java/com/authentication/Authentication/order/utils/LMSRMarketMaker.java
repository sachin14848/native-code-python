package com.order.order.utils;//package utils;

import java.util.Arrays;

//
//import java.util.Arrays;
//
public class LMSRMarketMaker {

    private final double[] shares;
    private double b;
    double floorPrice;


    // Constructor with initial shares to set base prices
    public LMSRMarketMaker(int outcomesCount, double b, double[] initialShares, double floorPrice) {
        this.shares = new double[outcomesCount];
        this.b = b;
        this.floorPrice = floorPrice;
        // Initialize the shares to set the base prices
        System.arraycopy(initialShares, 0, this.shares, 0, outcomesCount);
        System.out.println("initial shares : " + shares[0]);
        System.out.println("initial shares : " + shares[1]);
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
        System.out.println("buy " + outcome + " shares : " + shares[0]);
        System.out.println("buy " + outcome + " shares : " + shares[1]);


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
        double b = 5000.0;
        double floorPrice = 0.5;


        // Calculate the initial shares required to achieve the initial price
        double initialShares = b * Math.log(initialPrice * 2);

        // Set initial shares for both outcomes
        double[] initialSharesArray = {0, 0};
        LMSRMarketMaker marketMaker = new LMSRMarketMaker(2, b, initialSharesArray, floorPrice);

        // Initial prices
        System.out.println("Initial Yes price: " + marketMaker.getPrice(0) * 10);
        System.out.println("Initial No price: " + marketMaker.getPrice(1) * 10);
        for (int i = 0; i < 1000; i = i + 1) {

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
//        }
//        }

        }
        double costToBuy = marketMaker.buy(0, 5);
        System.out.println("Iteration " + "No" + ": Cost to buy 1 No share: " + costToBuy * 10);
        System.out.println("New Yes price: " + marketMaker.getPrice(0) * 10);
        System.out.println("New No price: " + marketMaker.getPrice(1) * 10);
    }
}

//public class LMSRMarketMaker {
//
//    private final OutcomePair outcomePair;
//    private double b;
//    private final double floorPrice;
//
//    // Constructor with initial shares to set base prices
//    public LMSRMarketMaker(double b, double initialSharesYes, double initialSharesNo, double floorPrice) {
//        this.outcomePair = new OutcomePair(new Test(0, initialSharesYes, "Yes"), new Test(1, initialSharesNo, "No"));
//        this.b = b;
//        this.floorPrice = floorPrice;
//    }
//
//    public double getPrice(int outcomeId) {
//        double sumExp = outcomePair.sumExp(b);
//        double rawPrice = Math.exp(outcomePair.getOutcome(outcomeId).getShare() / b) / sumExp;
//        return Math.max(rawPrice, floorPrice);
//    }
//
//    public double buy(int outcomeId, double numberOfShares) {
//        double  pair = outcomePair.sumExp(b);
//        double initialCost = b * Math.log(pair);
//
//        // Update the specific outcome share by adding numberOfShares
//        Test outcome = outcomePair.getOutcome(outcomeId);
//        outcome.setShare(outcome.getShare() + numberOfShares);
//        outcomePair.setOutcome(outcomeId, outcome);
//
//        double finalCost = b * Math.log(outcomePair.sumExp(b));
//        return finalCost - initialCost;
//    }
//
//    public void updateLiquidityParameter(double newB) {
//        this.b = newB;
//        System.out.println("Updated liquidity parameter to " + newB);
//    }
//
//    public static void main(String[] args) {
//        double initialPrice = 10.0;
//        double b = 10.0;
//        double floorPrice = 0.5;
//
//        double dd = Math.log(initialPrice * 2);
//        double initialSharesValue = b * dd;
//
//        LMSRMarketMaker marketMaker = new LMSRMarketMaker(b, initialSharesValue, initialSharesValue, floorPrice);
//
//        // Initial prices
//        System.out.println("Initial Yes price: " + marketMaker.getPrice(0) * 10);
//        System.out.println("Initial No price: " + marketMaker.getPrice(1) * 10);
//
//        for (int i = 0; i < 5; i++) {
//            double costToBuy = marketMaker.buy(1, 5);
//            System.out.println("Iteration " + (i + 1) + ": Cost to buy 1 No share: " + costToBuy * 10);
//            System.out.println("New Yes price: " + marketMaker.getPrice(0) * 10);
//            System.out.println("New No price: " + marketMaker.getPrice(1) * 10);
//        }
//    }
//}
//
//class Test {
//
//    private final int id;
//    private double share;
//    private final String text;
//
//    public Test(int id, double share, String text) {
//        this.id = id;
//        this.share = share;
//        this.text = text;
//    }
//
//    public double getShare() {
//        return share;
//    }
//
//    public void setShare(double share) {
//        this.share = share;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public String getText() {
//        return text;
//    }
//}
//
//class OutcomePair {
//
//
//    private Test yes;
//    private Test no;
//
//    public OutcomePair(Test yes, Test no) {
//        this.yes = yes;
//        this.no = no;
//    }
//
//    public Test getOutcome(int id) {
//        return id == 0 ? yes : no;
//    }
//
//    public void setOutcome(int id, Test outcome) {
//        if(id == 0){
//            yes = outcome;
//        }else{
//            no = outcome;
//        }
//    }
//
//    public double sumExp(double b) {
//        return Math.exp(yes.getShare() / b) + Math.exp(no.getShare() / b);
//    }
//}
//
//