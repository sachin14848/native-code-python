package com.question_generator.entity.question;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "yes_id")),
            @AttributeOverride(name = "outcomeName", column = @Column(name = "yes_outcome_name"))
    })
    private Outcome yes;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "no_id")),
            @AttributeOverride(name = "outcomeName", column = @Column(name = "no_outcome_name"))
    })
    private Outcome no;

    @OneToOne
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    @JsonBackReference
    private QuestionEntity question;

}
