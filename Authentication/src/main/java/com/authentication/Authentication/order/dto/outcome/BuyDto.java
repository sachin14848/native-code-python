package com.order.order.dto.outcome;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuyDto {


    @NotNull(message = "Order ID is required")
    private Long orderId;

    @NotNull(message = "Number of shares is required")
    @Min(value = 1, message = "Number of shares must be at least 1")
    @Max(value = 10, message = "Number of shares must be at most 10")
    private Double noOfShares;

    @NotNull(message = "Outcome ID is required")
    private Integer outcomeId;

}
