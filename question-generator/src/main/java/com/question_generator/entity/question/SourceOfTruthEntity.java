package com.question_generator.entity.question;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "SOURCE_0F_TRUTH")
//@SQLRestriction("is_active = true")
public class SourceOfTruthEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sourceOfTruth;
    private String sourceOfTruthDesc;
    private URL sourceOfTruthUrl;
    private Boolean isActive;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "sourceOfTruth", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionEntity> questions;


}
