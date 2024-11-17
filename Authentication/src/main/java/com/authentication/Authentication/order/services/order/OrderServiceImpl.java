package com.order.order.services.order;

import com.order.order.dto.PriceDto;
import com.order.order.dto.outcome.BuyDto;
import com.order.order.dto.outcome.OutcomeDto;
import com.order.order.entity.Status;
import com.order.order.entity.outcome.Outcome;
import com.order.order.entity.outcome.Outcomes;
import com.order.order.repo.OutcomeRepo;
import com.order.order.services.redis.PriceUpdatePublish;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@RequiredArgsConstructor
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    private final OutcomeRepo outcomeRepo;

    private final PriceUpdatePublish publish;


    @Override
    public Outcomes createOutcome(OutcomeDto outcomeDto) {
        Double initialPrice = outcomeDto.getInitialPrice();
        Double liquidity = outcomeDto.getLiquidity();
        if (initialPrice == null) {
            throw new IllegalStateException("Initial price not found");
        }
        if (liquidity == null) {
            throw new IllegalStateException("Liquidity not found");
        }
//        double dd = Math.log(outcomeDto.getInitialPrice() * 2);
//        double initialSharesValue = outcomeDto.getLiquidity() * dd;

        Outcome yes = outcomeDto.getYes();
        if (yes == null) {
            throw new IllegalStateException("Yes outcome not found");
        }
        yes.setShare(0.0);

        Outcome no = outcomeDto.getNo();
        if (no == null) {
            throw new IllegalStateException("No outcome not found");
        }
        no.setShare(0.0);
        Double floorPrice = outcomeDto.getFloorPrice();

        Outcomes outcomes = Outcomes.builder()
                .yes(yes)
                .no(no)
                .status(Status.OPEN)
//                .questionId(outcomeDto.getQuestionId())
                .liquidity(liquidity)
                .floorPrice(floorPrice == null ? 0.05 : floorPrice)
                .build();

        return outcomeRepo.save(outcomes);
    }

    @Override
    public void getPrice(Outcomes outcomes) {

        double[] shares = {outcomes.getYes().getShare(), outcomes.getNo().getShare()};
//        Double noShare = ;
        double liq = outcomes.getLiquidity();
        double floorPrice = outcomes.getFloorPrice();

//        double sumExp = Math.exp(yesShare / liq) + Math.exp(noShare / liq);
        PriceDto dto = PriceDto.builder()
                .orderId(outcomes.getId())
                .priceOfNo(calculatePrice(shares, shares[1], liq, floorPrice) * 10) // Pass shares[1] for "No"
                .priceOfYes(calculatePrice(shares, shares[0], liq, floorPrice) * 10) // Pass shares[0] for "Yes"
                .build();
        publish.handlePriceUpdate(dto);
    }

    private double calculatePrice(double[] shares, double outcomeShare, double b, double floorPrice) {
        log.info("Shares : {}", Arrays.toString(shares));
        double sumExp = Arrays.stream(shares)
                .map(q -> Math.exp(q / b))
                .sum();
        double rawPrice = Math.exp(outcomeShare / b) / sumExp;
        return Math.max(rawPrice, floorPrice);
    }

    @Override
    public Outcomes buy(BuyDto buyDto) {

        Outcomes outcomes = outcomeRepo.findById(buyDto.getOrderId())
                .orElseThrow(() -> new IllegalStateException("Order not found with Id: " + buyDto.getOrderId()));

        Outcome yes = outcomes.getYes();
        Outcome no = outcomes.getNo();

        // Update the specific outcome share by adding numberOfShares
        double numberOfShares = buyDto.getNoOfShares();
        int outcomeId = buyDto.getOutcomeId();
        if (yes.getId() == outcomeId) {
            yes.setShare(yes.getShare() + numberOfShares);
        } else if (no.getId() == outcomeId) {
            no.setShare(no.getShare() + numberOfShares);
        } else {
            throw new IllegalStateException("Invalid outcomeId: " + outcomeId);
        }
//        outcome.setShare(outcome.getShare() + numberOfShares);
        outcomes.setYes(yes);
        outcomes.setNo(no);

//        double finalCost = liquidity * Math.log(sumExp(liquidity, yes.getShare(), no.getShare()));
        getPrice(outcomes);
        return outcomeRepo.save(outcomes);
    }

    @Override
    public void updateLiquidityParameter(double newB) {

    }

    private double sumExp(double b, double yes, double no) {
        return Math.exp(yes / b) + Math.exp(no / b);
    }

}
