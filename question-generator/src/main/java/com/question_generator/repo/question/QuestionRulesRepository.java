package com.question_generator.repo.question;

import com.question_generator.entity.question.QuestionRules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRulesRepository extends JpaRepository<QuestionRules, Long> {
}
