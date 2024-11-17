package com.order.order.entity.outcome;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.order.order.entity.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "OUTCOMES")
public class Outcomes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "question_id")
//    private Long questionId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "yes_id")),
            @AttributeOverride(name = "outcomeName", column = @Column(name = "yes_outcome_name")),
            @AttributeOverride(name = "share", column = @Column(name = "yes_share"))
    })
    private Outcome yes;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "no_id")),
            @AttributeOverride(name = "outcomeName", column = @Column(name = "no_outcome_name")),
            @AttributeOverride(name = "share", column = @Column(name = "no_share"))
    })
    private Outcome no;

    private Double liquidity;
    private Double floorPrice;

    @Enumerated(EnumType.STRING)
    private Status status = Status.OPEN;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime deactivatedAt;


}
