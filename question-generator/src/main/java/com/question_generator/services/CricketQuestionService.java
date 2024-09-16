package com.question_generator.services;

import com.question_generator.dto.CricketDto;
import com.question_generator.entity.CricketQuestion;
import com.question_generator.repo.CricketQuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class CricketQuestionService {

    private final CricketQuestionRepository cricketQuestionRepository;

    public void createQuestion(CricketDto cricketDto) {
        try {
            CricketQuestion question = new CricketQuestion();
            question.setQuestion(cricketDto.getQuestion());
            cricketQuestionRepository.save(question);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        System.out.println("Question saved successfully");
    }

    public List<CricketDto> getCricketQuestions() {
        return cricketQuestionRepository.findAll().stream().map(CricketDto::new).toList();
    }

}
