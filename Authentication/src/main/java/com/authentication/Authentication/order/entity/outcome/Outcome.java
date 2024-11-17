package com.order.order.entity.outcome;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Outcome {


    private int id;

    @Column(name = "outcome_name")
    private String outcomeName;

    @Column(name = "share_price")
    private Double share;

}