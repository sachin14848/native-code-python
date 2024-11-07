package com.question_generator.entity.event;

import com.question_generator.entity.question.QuestionEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events")
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String eventName;
    private String eventDescription;

    @CreationTimestamp
    private LocalDate createdAt;

    @OneToMany(mappedBy = "eventEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionEntity> questions;

}
