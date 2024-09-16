package com.question_generator.repo;

import com.question_generator.entity.CricketQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CricketQuestionRepository extends JpaRepository<CricketQuestion, Long> {
}
