package com.order.order.dto.outcome;

import com.order.order.entity.outcome.Outcome;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OutcomeDto {

    private Long id;

    @NotNull(message = "Yes outcome is required")
    private Outcome yes;
    @NotNull(message = "No outcome is required")
    private Outcome no;

    @NotNull(message = "liquidity is required")
    private Double liquidity;

    @NotNull(message = "floorPrice is required")
    @DecimalMin(value = "0.03", message = "floorPrice must be at least 0.03")
    @DecimalMax(value = "0.1", message = "floorPrice must be at most 0.1")
    private Double floorPrice;

    @NotNull(message = "initialPrice is required")
    private Double initialPrice;

//    @NotNull(message = "Question ID is required")
//    private Long questionId;
}
