package com.neil.survey.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.neil.survey.module.Answer;

public interface AnswerRepository extends JpaRepository<Answer,Long> {
	List<Answer> findByAnswerId(String answerId);
	List<Answer> findBySurveyId(String surveyId);	
	
	
}
