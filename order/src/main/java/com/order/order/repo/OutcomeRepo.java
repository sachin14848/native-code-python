package com.question_generator.repo.question;

import com.question_generator.entity.question.Outcomes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutcomeRepo extends JpaRepository<Outcomes, Long> {
}
