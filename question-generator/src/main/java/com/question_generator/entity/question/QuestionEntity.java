package com.question_generator.entity.question;

import com.question_generator.entity.event.EventEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "questions")
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private boolean isActive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_of_truth_id", nullable = false, referencedColumnName = "id")
    private SourceOfTruthEntity sourceOfTruth;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false, referencedColumnName = "id")
    private EventEntity eventEntity;

    @ManyToMany
    @JoinTable(
            name = "question_question_rules",
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "rule_id")
    )
    private List<QuestionRules> rules;


}
