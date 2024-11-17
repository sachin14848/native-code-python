package com.order.order.services.order;

import com.order.order.dto.outcome.BuyDto;
import com.order.order.dto.outcome.OutcomeDto;
import com.order.order.entity.outcome.Outcomes;

public interface OrderService {

    Outcomes createOutcome(OutcomeDto outcomeDto);

    void getPrice(Outcomes outcome);

    Outcomes buy(BuyDto buyDto);

    void updateLiquidityParameter(double newB);

}
